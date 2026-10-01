package com.trading.events.infrastructure.controller;

import com.trading.events.domain.model.Event;
import com.trading.events.domain.model.Event.EventStatus;
import com.trading.events.domain.model.Event.EventType;
import com.trading.events.domain.port.EventProcessor;
import com.trading.events.domain.exception.EventProcessingException;
import com.trading.events.application.EventProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    private final EventProcessingService eventProcessingService;
    private final EventProcessor eventProcessor;
    private final AtomicLong eventsReceived = new AtomicLong(0);
    private final AtomicLong eventsProcessed = new AtomicLong(0);
    private final AtomicLong eventsFailed = new AtomicLong(0);
    private final ConcurrentHashMap<String, Event> processedEvents = new ConcurrentHashMap<>();

    public EventController(EventProcessingService eventProcessingService, EventProcessor eventProcessor) {
        this.eventProcessingService = eventProcessingService;
        this.eventProcessor = eventProcessor;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Map<String, Object>>> receiveEvent(@RequestBody EventRequest request) {
        eventsReceived.incrementAndGet();
        log.info("Evento recibido: accountId={}, type={}", request.accountId(), request.eventType());

        return Mono.fromCallable(() -> Event.createNewEvent(request.accountId(), 
                EventType.valueOf(request.eventType())))
            .flatMap(event -> {
                try {
                    event.validate();
                } catch (IllegalArgumentException e) {
                    eventsFailed.incrementAndGet();
                    return Mono.error(new EventProcessingException(
                        event.eventId(), event.accountId(), "Invalid event: " + e.getMessage()));
                }
                return eventProcessingService.processEventWithConcurrency(event);
            })
            .doOnSuccess(result -> {
                eventsProcessed.incrementAndGet();
                processedEvents.put(result.eventId(), result);
                log.info("Evento procesado exitosamente: {}", result.eventId());
            })
            .doOnError(error -> {
                eventsFailed.incrementAndGet();
                log.error("Error al procesar evento: {}", error.getMessage());
            })
            .map(event -> ResponseEntity.ok(Map.of(
                "eventId", event.eventId(),
                "accountId", event.accountId(),
                "status", event.status().toString(),
                "timestamp", event.timestamp().toString()
            )))
            .onErrorResume(EventProcessingException.class, ex -> 
                Mono.just(ResponseEntity.badRequest().body(Map.of(
                    "error", ex.getMessage(),
                    "eventId", ex.getEventId() != null ? ex.getEventId() : "unknown",
                    "accountId", ex.getAccountId() != null ? ex.getAccountId() : "unknown"
                ))));
    }

    @PostMapping(value = "/batch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Map<String, Object>>> receiveBatch(@RequestBody BatchEventRequest request) {
        log.info("Recibido batch de {} eventos con concurrencia máxima {}", 
                request.events().size(), request.maxConcurrency());

        int maxConcurrency = request.maxConcurrency() > 0 ? request.maxConcurrency() : 10;

        return Flux.fromIterable(request.events())
            .flatMap(eventReq -> Mono.fromCallable(() -> 
                Event.createNewEvent(eventReq.accountId(), EventType.valueOf(eventReq.eventType()))
            ).flatMap(event -> {
                try {
                    event.validate();
                } catch (IllegalArgumentException e) {
                    return Mono.error(new EventProcessingException(
                        event.eventId(), event.accountId(), "Invalid event: " + e.getMessage()));
                }
                return eventProcessingService.processEventWithConcurrency(event);
            }), maxConcurrency)
            .collectList()
            .doOnSuccess(results -> {
                long successCount = results.stream().filter(e -> e.status() == EventStatus.COMPLETED).count();
                long failedCount = results.size() - successCount;
                eventsReceived.addAndGet(results.size());
                eventsProcessed.addAndGet(successCount);
                eventsFailed.addAndGet(failedCount);
                results.forEach(e -> processedEvents.put(e.eventId(), e));
                log.info("Batch procesado: {} exitosos, {} fallidos", successCount, failedCount);
            })
            .map(results -> ResponseEntity.ok(Map.of(
                "total", results.size(),
                "processed", results.stream().filter(e -> e.status() == EventStatus.COMPLETED).count(),
                "failed", results.stream().filter(e -> e.status() == EventStatus.FAILED).count()
            )));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Map<String, Object>>> getEventStatus(@RequestParam String eventId) {
        return Mono.fromCallable(() -> processedEvents.get(eventId))
            .map(event -> {
                if (event == null) {
                    return ResponseEntity.notFound().<Map<String, Object>>build();
                }
                return ResponseEntity.ok(Map.of(
                    "eventId", event.eventId(),
                    "accountId", event.accountId(),
                    "type", event.eventType().toString(),
                    "status", event.status().toString(),
                    "timestamp", event.timestamp().toString()
                ));
            });
    }

    @GetMapping(value = "/monitor", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Map<String, Object>>> getMonitorStats() {
        return Mono.just(ResponseEntity.ok(Map.of(
            "eventsReceived", eventsReceived.get(),
            "eventsProcessed", eventsProcessed.get(),
            "eventsFailed", eventsFailed.get(),
            "processedEventsCount", processedEvents.size(),
            "timestamp", Instant.now().toString()
        )));
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Map<String, Object>> eventStream() {
        return Flux.interval(java.time.Duration.ofSeconds(2))
            .map(tick -> Map.of(
                "eventsReceived", eventsReceived.get(),
                "eventsProcessed", eventsProcessed.get(),
                "eventsFailed", eventsFailed.get(),
                "timestamp", Instant.now().toString()
            ));
    }

    public record EventRequest(String accountId, String eventType) {}

    public record BatchEventRequest(java.util.List<EventRequest> events, int maxConcurrency) {}
}