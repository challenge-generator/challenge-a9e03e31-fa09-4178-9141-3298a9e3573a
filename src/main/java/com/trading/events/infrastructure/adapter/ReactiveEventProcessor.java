package com.trading.events.infrastructure.adapter;

import com.trading.events.domain.exception.EventProcessingException;
import com.trading.events.domain.model.Event;
import com.trading.events.domain.port.EventProcessor;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.RejectedExecutionException;
import java.util.function.Function;

@Component
public class ReactiveEventProcessor implements EventProcessor {

    private static final Logger log = LoggerFactory.getLogger(ReactiveEventProcessor.class);
    private static final int MAX_CONCURRENT_PROCESSING = 20;
    private static final Duration PROCESSING_TIMEOUT = Duration.ofSeconds(30);
    private static final int RETRY_ATTEMPTS = 2;

    private final io.github.resilience4j.circuitbreaker.CircuitBreaker circuitBreaker;

    public ReactiveEventProcessor(io.github.resilience4j.circuitbreaker.CircuitBreaker circuitBreaker) {
        this.circuitBreaker = circuitBreaker;
    }

    @Override
    @CircuitBreaker(name = "eventProcessing", fallbackMethod = "processEventFallback")
    public Mono<Event> processEvent(Event event) {
        return Mono.just(event)
            .doOnSubscribe(s -> log.debug("Starting processing for event: {}", event.eventId()))
            .map(this::validateEvent)
            .map(this::markAsProcessing)
            .flatMap(this::executeProcessing)
            .timeout(PROCESSING_TIMEOUT)
            .doOnSuccess(result -> log.info("Event {} processed successfully at {}",
                result.eventId(), Instant.now()))
            .doOnError(error -> log.error("Event {} processing failed: {}",
                event.eventId(), error.getMessage(), error));
    }

    private Event validateEvent(Event event) {
        try {
            event.validate();
        } catch (IllegalArgumentException e) {
            throw EventProcessingException.forInvalidEvent(
                event.eventId(),
                event.accountId(),
                e.getMessage()
            );
        }
        return event;
    }

    private Event markAsProcessing(Event event) {
        return event.markAsProcessing();
    }

    private Mono<Event> executeProcessing(Event event) {
        return Mono.fromCallable(() -> {
            simulateProcessingWork(event);
            return event.markAsCompleted();
        })
        .subscribeOn(Schedulers.boundedElastic())
        .publishOn(Schedulers.parallel())
        .retryWhen(reactor.retry.Retry.backoff(RETRY_ATTEMPTS, Duration.ofMillis(50))
            .filter(throwable -> throwable instanceof RejectedExecutionException));
    }

    private void simulateProcessingWork(Event event) {
        try {
            Thread.sleep(10 + (long) (Math.random() * 50));
            if (Math.random() < 0.01) {
                throw new RuntimeException("Random processing error");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new EventProcessingException(
                event.eventId(),
                event.accountId(),
                "Processing interrupted",
                e
            );
        }
    }

    private Mono<Event> processEventFallback(Event event, Throwable cause) {
        log.warn("Circuit breaker fallback triggered for event: {}, cause: {}",
            event.eventId(), cause.getMessage());
        return Mono.just(event.markAsFailed());
    }

    @Override
    public Mono<Void> processEventsInParallel(Iterable<Event> events, int maxConcurrency) {
        return Flux.fromIterable(events)
            .flatMap(this::processEvent, maxConcurrency)
            .then()
            .doOnSuccess(v -> log.info("All events processed in parallel with concurrency: {}", maxConcurrency));
    }

    public Flux<Event> processEventStream(Flux<Event> eventStream) {
        return eventStream
            .window(Duration.ofSeconds(1))
            .flatMap(window -> window
                .flatMap(this::processEvent)
                .collectList()
                .filter(list -> !list.isEmpty())
                .flatMapMany(processed -> {
                    log.info("Processed batch of {} events", processed.size());
                    return Flux.fromIterable(processed);
                }));
    }

    public Mono<Event> processWithCircuitBreaker(Event event) {
        return Mono.defer(() -> processEvent(event))
            .transformDeferred(mono ->
                io.github.resilience4j.reactor.MonoCircuitBreaker.of(circuitBreaker, mono)
            );
    }

    public Mono<Event> processWithRetryAndCircuitBreaker(Event event) {
        return processEvent(event)
            .retryWhen(reactor.retry.Retry.backoff(3, Duration.ofSeconds(1))
                .filter(throwable -> throwable instanceof EventProcessingException)
                .doBeforeRetry(signal -> {
                    log.info("Retrying event {} attempt {}",
                        event.eventId(), signal.totalRetries() + 1);
                }))
            .transformDeferred(mono ->
                io.github.resilience4j.reactor.MonoCircuitBreaker.of(circuitBreaker, mono)
            );
    }

    public Function<Event, Mono<Event>> createProcessingPipeline() {
        return event -> processEvent(event)
            .subscribeOn(Schedulers.boundedElastic())
            .publishOn(Schedulers.parallel())
            .cache(1);
    }

    @Override
    public boolean isAvailable() {
        return circuitBreaker.getState() != io.github.resilience4j.circuitbreaker.CircuitBreaker.State.OPEN;
    }
}