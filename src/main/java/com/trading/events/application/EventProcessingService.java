package com.trading.events.application;

import com.trading.events.domain.exception.EventProcessingException;
import com.trading.events.domain.model.Event;
import com.trading.events.domain.port.EventProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EventProcessingService {

    private static final Logger log = LoggerFactory.getLogger(EventProcessingService.class);
    private static final int DEFAULT_BATCH_SIZE = 100;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MS = 100;

    private final EventProcessor eventProcessor;
    private final ExecutorService dedicatedExecutor;
    private final Semaphore concurrencyLimiter;
    private final AtomicInteger activeEvents;
    private final AtomicLong totalProcessed;
    private final AtomicLong totalFailed;
    private final ConcurrentHashMap<String, Instant> processingStartTimes;

    public EventProcessingService(EventProcessor eventProcessor, ExecutorService executor) {
        this.eventProcessor = eventProcessor;
        this.dedicatedExecutor = executor;
        this.concurrencyLimiter = new Semaphore(50);
        this.activeEvents = new AtomicInteger(0);
        this.totalProcessed = new AtomicLong(0);
        this.totalFailed = new AtomicLong(0);
        this.processingStartTimes = new ConcurrentHashMap<>();
    }

    public Mono<Event> processEvent(Event event) {
        return Mono.fromCallable(() -> {
            if (!concurrencyLimiter.tryAcquire()) {
                log.warn("Concurrency limit reached for event: {}", event.eventId());
                throw EventProcessingException.forProcessingFailure(
                    event.eventId(),
                    event.accountId(),
                    new RejectedExecutionException("Concurrency limit exceeded")
                );
            }
            return event;
        })
        .doOnNext(e -> {
            activeEvents.incrementAndGet();
            processingStartTimes.put(e.eventId(), Instant.now());
            log.debug("Event {} started processing. Active: {}", e.eventId(), activeEvents.get());
        })
        .flatMap(eventProcessor::processEvent)
        .doOnSuccess(result -> {
            activeEvents.decrementAndGet();
            totalProcessed.incrementAndGet();
            processingStartTimes.remove(result.eventId());
            log.info("Event {} completed successfully. Active: {}, Total: {}",
                result.eventId(), activeEvents.get(), totalProcessed.get());
        })
        .doOnError(error -> {
            activeEvents.decrementAndGet();
            totalFailed.incrementAndGet();
            log.error("Event {} failed. Active: {}, Total Failed: {}",
                event.eventId(), activeEvents.get(), totalFailed.get(), error);
        })
        .doFinally(signal -> concurrencyLimiter.release())
        .retryWhen(Retry.backoff(MAX_RETRY_ATTEMPTS, Duration.ofMillis(RETRY_DELAY_MS))
            .filter(throwable -> throwable instanceof EventProcessingException)
            .doBeforeRetry(retrySignal -> {
                log.warn("Retrying event {} attempt {}", event.eventId(), retrySignal.totalRetries() + 1);
            }));
    }

    public Flux<Event> processBatch(List<Event> events) {
        return Flux.fromIterable(events)
            .flatMap(this::processEvent, 10)
            .collectList()
            .flatMapMany(processed -> {
                log.info("Batch processed: {} events", processed.size());
                return Flux.fromIterable(processed);
            });
    }

    public Mono<List<Event>> processWithParallelism(List<Event> events, int maxConcurrency) {
        return Flux.fromIterable(events)
            .parallel(maxConcurrency)
            .runOn(Schedulers.boundedElastic())
            .flatMap(this::processEvent)
            .sequential()
            .collectList();
    }

    public Mono<Void> processEventsAsync(List<Event> events) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (Event event : events) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    processEvent(event).block();
                } catch (Exception e) {
                    log.error("Async processing failed for event: {}", event.eventId(), e);
                }
            }, dedicatedExecutor);
            futures.add(future);
        }

        return Mono.fromFuture(
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
        ).then();
    }

    public Mono<Integer> processWithBackpressure(List<Event> events, int bufferSize) {
        return Flux.fromIterable(events)
            .buffer(bufferSize)
            .flatMap(batch -> processBatch(batch).then(Mono.just(batch.size())))
            .reduce(Integer::sum);
    }

    public int getActiveEventCount() {
        return activeEvents.get();
    }

    public long getTotalProcessed() {
        return totalProcessed.get();
    }

    public long getTotalFailed() {
        return totalFailed.get();
    }

    public ConcurrentHashMap<String, Instant> getProcessingTimes() {
        return new ConcurrentHashMap<>(processingStartTimes);
    }

    public void shutdown() {
        log.info("Shutting down EventProcessingService");
        dedicatedExecutor.shutdown();
        try {
            if (!dedicatedExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                dedicatedExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            dedicatedExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}