package com.trading.events.infrastructure.adapter;


import com.trading.events.domain.model.EventType;
import com.trading.events.domain.model.Event;
import com.trading.events.domain.model.Event.EventStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

@Component
public class EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(EventPublisher.class);

    private final Sinks.Many<Event> eventSink;
    private final CopyOnWriteArrayList<PublishedEvent> publishedEvents;
    private final AtomicLong totalPublished;
    private final AtomicLong totalAcknowledged;
    private volatile Consumer<Event> eventAcknowledgmentHandler;

    public EventPublisher() {
        this.eventSink = Sinks.many().multicast().directBestEffort();
        this.publishedEvents = new CopyOnWriteArrayList<>();
        this.totalPublished = new AtomicLong(0);
        this.totalAcknowledged = new AtomicLong(0);
    }

    public Mono<Boolean> publishEvent(Event event) {
        return Mono.fromRunnable(() -> {
            log.debug("Publicando evento: {}", event.eventId());
            Sinks.EmitResult result = eventSink.emitNext(event, Sinks.EmitFailureHandler.FAIL_FAST);
            
            if (result == Sinks.EmitResult.OK) {
                totalPublished.incrementAndGet();
                publishedEvents.add(new PublishedEvent(
                    event.eventId(), 
                    event.accountId(), 
                    event.eventType(), 
                    Instant.now(),
                    true
                ));
                log.info("Evento publicado exitosamente: {}", event.eventId());
            } else {
                log.warn("Falló la publicación del evento: {}, causa: {}", event.eventId(), result);
                publishedEvents.add(new PublishedEvent(
                    event.eventId(), 
                    event.accountId(), 
                    event.eventType(), 
                    Instant.now(),
                    false
                ));
            }
        });
    }

    public Mono<Boolean> publishEvents(List<Event> events) {
        return Flux.fromIterable(events)
            .flatMap(this::publishEvent, 5)
            .then(Mono.just(true))
            .onErrorResume(e -> {
                log.error("Error en publicación masiva: {}", e.getMessage());
                return Mono.just(false);
            });
    }

    public Flux<Event> eventStream() {
        return eventSink.asFlux();
    }

    public Mono<Void> acknowledgeEvent(String eventId) {
        return Mono.fromRunnable(() -> {
            totalAcknowledged.incrementAndGet();
            if (eventAcknowledgmentHandler != null) {
                publishedEvents.stream()
                    .filter(e -> e.eventId().equals(eventId))
                    .findFirst()
                    .ifPresent(e -> {
                        Event event = Event.createNewEvent(e.accountId(), e.eventType());
                        eventAcknowledgmentHandler.accept(event.markAsCompleted());
                    });
            }
            log.debug("Evento reconocido: {}", eventId);
        });
    }

    public void setAcknowledgmentHandler(Consumer<Event> handler) {
        this.eventAcknowledgmentHandler = handler;
    }

    public Mono<PublisherStats> getStats() {
        return Mono.fromCallable(() -> new PublisherStats(
            totalPublished.get(),
            totalAcknowledged.get(),
            publishedEvents.size(),
            eventSink.currentSubscriberCount()
        ));
    }

    public Mono<List<PublishedEvent>> getRecentEvents(int limit) {
        return Mono.fromCallable(() -> 
            publishedEvents.stream()
                .skip(Math.max(0, publishedEvents.size() - limit))
                .toList()
        );
    }

    public record PublishedEvent(
        String eventId,
        String accountId,
        com.trading.events.domain.model.Event.EventType eventType,
        Instant publishedAt,
        boolean success
    ) {}

    public record PublisherStats(
        long totalPublished,
        long totalAcknowledged,
        int queuedEvents,
        int activeSubscribers
    ) {}
}