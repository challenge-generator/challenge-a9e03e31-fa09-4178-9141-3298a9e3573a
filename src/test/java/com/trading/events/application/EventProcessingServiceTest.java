package com.trading.events.application;

import com.trading.events.domain.exception.EventProcessingException;
import com.trading.events.domain.model.Event;
import com.trading.events.domain.model.Event.EventStatus;
import com.trading.events.domain.model.Event.EventType;
import com.trading.events.domain.port.EventProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para EventProcessingService - Concurrencia y Sincronización")
class EventProcessingServiceTest {

    @Mock
    private EventProcessor eventProcessor;

    @InjectMocks
    private EventProcessingService eventProcessingService;

    private Event testEvent;
    private Event testEvent2;
    private Event testEvent3;

    @BeforeEach
    void setUp() {
        testEvent = Event.createNewEvent("ACC-001", EventType.ACCOUNT_UPDATE);
        testEvent2 = Event.createNewEvent("ACC-002", EventType.TRADE_EXECUTION);
        testEvent3 = Event.createNewEvent("ACC-003", EventType.RISK_ALERT);
    }

    @Test
    @DisplayName("Debe procesar un evento individual correctamente")
    void shouldProcessSingleEventSuccessfully() {
        when(eventProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    Event event = invocation.getArgument(0);
                    return Mono.just(event.markAsCompleted());
                });

        Mono<Event> result = eventProcessingService.processEvent(testEvent);

        StepVerifier.create(result)
                .expectNextMatches(event -> event.status() == EventStatus.COMPLETED)
                .verifyComplete();

        verify(eventProcessor, times(1)).processEvent(any(Event.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el procesamiento falla")
    void shouldThrowExceptionWhenProcessingFails() {
        when(eventProcessor.processEvent(any(Event.class)))
                .thenReturn(Mono.error(new EventProcessingException(
                        testEvent.id(),
                        testEvent.accountId(),
                        "Error en procesamiento"
                )));

        StepVerifier.create(eventProcessingService.processEvent(testEvent))
                .expectError(EventProcessingException.class)
                .verify();
    }

    @Test
    @DisplayName("Debe procesar múltiples eventos en paralelo")
    void shouldProcessMultipleEventsInParallel() throws InterruptedException {
        when(eventProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    Thread.sleep(50);
                    Event event = invocation.getArgument(0);
                    return Mono.just(event.markAsCompleted());
                });

        List<Event> events = List.of(testEvent, testEvent2, testEvent3);
        CountDownLatch latch = new CountDownLatch(events.size());
        AtomicInteger processedCount = new AtomicInteger(0);

        long startTime = System.currentTimeMillis();

        Flux.fromIterable(events)
                .flatMap(event -> eventProcessingService.processEvent(event)
                        .doOnSuccess(e -> {
                            processedCount.incrementAndGet();
                            latch.countDown();
                        })
                        .onErrorResume(e -> Mono.empty()))
                .blockLast();

        latch.await(5, TimeUnit.SECONDS);
        long duration = System.currentTimeMillis() - startTime;

        assertThat(processedCount.get()).isEqualTo(3);
        assertThat(duration).isLessThan(200);
        verify(eventProcessor, times(3)).processEvent(any(Event.class));
    }

    @Test
    @DisplayName("Debe respetar el límite de concurrencia configurado")
    void shouldRespectConcurrencyLimit() {
        AtomicInteger concurrentExecutions = new AtomicInteger(0);
        AtomicInteger maxConcurrent = new AtomicInteger(0);

        when(eventProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    int current = concurrentExecutions.incrementAndGet();
                    maxConcurrent.set(Math.max(maxConcurrent.get(), current));
                    Thread.sleep(100);
                    concurrentExecutions.decrementAndGet();
                    Event event = invocation.getArgument(0);
                    return Mono.just(event.markAsCompleted());
                });

        List<Event> events = List.of(
                Event.createNewEvent("ACC-001", EventType.ACCOUNT_UPDATE),
                Event.createNewEvent("ACC-002", EventType.TRADE_EXECUTION),
                Event.createNewEvent("ACC-003", EventType.RISK_ALERT),
                Event.createNewEvent("ACC-004", EventType.ACCOUNT_UPDATE),
                Event.createNewEvent("ACC-005", EventType.TRADE_EXECUTION)
        );

        eventProcessingService.processEventsInBatch(events)
                .as(StepVerifier::create)
                .expectNextCount(5)
                .verifyComplete();

        assertThat(maxConcurrent.get()).isLessThanOrEqualTo(3);
    }

    @Test
    @DisplayName("Debe validar eventos antes del procesamiento")
    void shouldValidateEventsBeforeProcessing() {
        Event invalidEvent = Event.createNewEvent("", EventType.UNKNOWN);

        assertThatThrownBy(() -> eventProcessingService.processEvent(invalidEvent))
                .isInstanceOf(IllegalArgumentException.class);

        verify(eventProcessor, never()).processEvent(any(Event.class));
    }

    @Test
    @DisplayName("Debe manejar errores de cuenta correctamente")
    void shouldHandleAccountErrorsCorrectly() {
        when(eventProcessor.processEvent(any(Event.class)))
                .thenReturn(Mono.error(EventProcessingException.forAccountUpdateFailure(
                        testEvent.id(),
                        testEvent.accountId(),
                        new RuntimeException("Conexión fallida")
                )));

        StepVerifier.create(eventProcessingService.processEvent(testEvent))
                .expectErrorMatches(error ->
                        error instanceof EventProcessingException &&
                        ((EventProcessingException) error).getAccountId().equals(testEvent.accountId()))
                .verify();
    }

    @Test
    @DisplayName("Debe procesar eventos con diferentes tipos correctamente")
    void shouldProcessEventsWithDifferentTypes() {
        when(eventProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0).markAsCompleted()));

        List<Event> events = List.of(
                Event.createNewEvent("ACC-001", EventType.ACCOUNT_UPDATE),
                Event.createNewEvent("ACC-002", EventType.TRADE_EXECUTION),
                Event.createNewEvent("ACC-003", EventType.RISK_ALERT)
        );

        Flux<Event> results = eventProcessingService.processEventsInBatch(events);

        StepVerifier.create(results)
                .assertNext(event -> assertThat(event.eventType()).isEqualTo(EventType.ACCOUNT_UPDATE))
                .assertNext(event -> assertThat(event.eventType()).isEqualTo(EventType.TRADE_EXECUTION))
                .assertNext(event -> assertThat(event.eventType()).isEqualTo(EventType.RISK_ALERT))
                .verifyComplete();
    }

    @Test
    @DisplayName("Debe verificar disponibilidad del procesador antes de procesar")
    void shouldCheckProcessorAvailabilityBeforeProcessing() {
        when(eventProcessor.isAvailable()).thenReturn(false);

        Mono<Event> result = eventProcessingService.processEvent(testEvent);

        StepVerifier.create(result)
                .expectErrorMatches(error ->
                        error instanceof EventProcessingException &&
                        error.getMessage().contains("no disponible"))
                .verify();

        verify(eventProcessor, never()).processEvent(any(Event.class));
    }
}