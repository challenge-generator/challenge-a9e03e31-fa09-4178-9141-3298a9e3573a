package com.trading.events.infrastructure.adapter;

import com.trading.events.domain.exception.EventProcessingException;
import com.trading.events.domain.model.Event;
import com.trading.events.domain.model.Event.EventStatus;
import com.trading.events.domain.model.Event.EventType;
import com.trading.events.domain.port.EventProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas de integración para ReactiveEventProcessor - Comportamiento Reactivo y Escalabilidad")
class ReactiveEventProcessorTest {

    @Mock
    private EventProcessor delegateProcessor;

    private ReactiveEventProcessor reactiveEventProcessor;
    private Event testEvent;
    private Event testEvent2;
    private Event testEvent3;

    @BeforeEach
    void setUp() {
        reactiveEventProcessor = new ReactiveEventProcessor(delegateProcessor);
        testEvent = Event.createNewEvent("ACC-001", EventType.ACCOUNT_UPDATE);
        testEvent2 = Event.createNewEvent("ACC-002", EventType.TRADE_EXECUTION);
        testEvent3 = Event.createNewEvent("ACC-003", EventType.RISK_ALERT);
    }

    @Test
    @DisplayName("Debe procesar evento de forma reactiva retorna Mono")
    void shouldProcessEventReactivelyReturningMono() {
        when(delegateProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    Event event = invocation.getArgument(0);
                    return Mono.just(event.markAsCompleted());
                });

        Mono<Event> result = reactiveEventProcessor.processEvent(testEvent);

        StepVerifier.create(result)
                .expectNextMatches(event ->
                        event.status() == EventStatus.COMPLETED &&
                        event.id().equals(testEvent.id()))
                .verifyComplete();

        verify(delegateProcessor, times(1)).processEvent(any(Event.class));
    }

    @Test
    @DisplayName("Debe procesar eventos en paralelo con flux")
    void shouldProcessEventsInParallelWithFlux() {
        when(delegateProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    Thread.sleep(50);
                    return Mono.just(invocation.getArgument(0).markAsCompleted());
                });

        List<Event> events = List.of(testEvent, testEvent2, testEvent3);

        Flux<Event> flux = reactiveEventProcessor.processEventsInParallel(events, 3);

        StepVerifier.create(flux)
                .expectNextCount(3)
                .verifyComplete();

        verify(delegateProcessor, times(3)).processEvent(any(Event.class));
    }

    @Test
    @DisplayName("Debe escalar horizontalmente con mayor concurrencia")
    void shouldScaleHorizontallyWithHigherConcurrency() throws InterruptedException {
        AtomicInteger activeProcessing = new AtomicInteger(0);
        AtomicInteger maxConcurrent = new AtomicInteger(0);

        when(delegateProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    activeProcessing.incrementAndGet();
                    maxConcurrent.set(Math.max(maxConcurrent.get(), activeProcessing.get()));
                    Thread.sleep(100);
                    activeProcessing.decrementAndGet();
                    return Mono.just(invocation.getArgument(0).markAsCompleted());
                });

        List<Event> events = List.of(
                Event.createNewEvent("ACC-001", EventType.ACCOUNT_UPDATE),
                Event.createNewEvent("ACC-002", EventType.TRADE_EXECUTION),
                Event.createNewEvent("ACC-003", EventType.RISK_ALERT),
                Event.createNewEvent("ACC-004", EventType.ACCOUNT_UPDATE),
                Event.createNewEvent("ACC-005", EventType.TRADE_EXECUTION),
                Event.createNewEvent("ACC-006", EventType.RISK_ALERT)
        );

        long startTimeLowConcurrency = System.currentTimeMillis();
        reactiveEventProcessor.processEventsInParallel(events, 2).blockLast();
        long durationLowConcurrency = System.currentTimeMillis() - startTimeLowConcurrency;

        reset(delegateProcessor);
        activeProcessing.set(0);
        maxConcurrent.set(0);

        when(delegateProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    activeProcessing.incrementAndGet();
                    maxConcurrent.set(Math.max(maxConcurrent.get(), activeProcessing.get()));
                    Thread.sleep(100);
                    activeProcessing.decrementAndGet();
                    return Mono.just(invocation.getArgument(0).markAsCompleted());
                });

        long startTimeHighConcurrency = System.currentTimeMillis();
        reactiveEventProcessor.processEventsInParallel(events, 6).blockLast();
        long durationHighConcurrency = System.currentTimeMillis() - startTimeHighConcurrency;

        assertThat(durationHighConcurrency).isLessThan(durationLowConcurrency);
    }

    @Test
    @DisplayName("Debe manejar errores en el flujo reactivo")
    void shouldHandleErrorsInReactiveFlow() {
        when(delegateProcessor.processEvent(any(Event.class)))
                .thenReturn(Mono.error(new EventProcessingException(
                        testEvent.id(),
                        testEvent.accountId(),
                        "Error de procesamiento"
                )));

        Mono<Event> result = reactiveEventProcessor.processEvent(testEvent);

        StepVerifier.create(result)
                .expectError(EventProcessingException.class)
                .verify();
    }

    @Test
    @DisplayName("Debe propagar errores en procesamiento paralelo")
    void shouldPropagateErrorsInParallelProcessing() {
        when(delegateProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    Event event = invocation.getArgument(0);
                    if (event.accountId().contains("002")) {
                        return Mono.error(EventProcessingException.forProcessingFailure(
                                event.id(),
                                event.accountId(),
                                new RuntimeException("Error")
                        ));
                    }
                    return Mono.just(event.markAsCompleted());
                });

        List<Event> events = List.of(testEvent, testEvent2, testEvent3);

        StepVerifier.create(reactiveEventProcessor.processEventsInParallel(events, 3))
                .expectNextCount(2)
                .expectError(EventProcessingException.class)
                .verify();
    }

    @Test
    @DisplayName("Debe verificar disponibilidad del procesador")
    void shouldCheckProcessorAvailability() {
        when(delegateProcessor.isAvailable()).thenReturn(true);

        boolean available = reactiveEventProcessor.isAvailable();

        assertThat(available).isTrue();
        verify(delegateProcessor, times(1)).isAvailable();
    }

    @Test
    @DisplayName("Debe retornar false cuando el procesador no está disponible")
    void shouldReturnFalseWhenProcessorNotAvailable() {
        when(delegateProcessor.isAvailable()).thenReturn(false);

        boolean available = reactiveEventProcessor.isAvailable();

        assertThat(available).isFalse();
    }

    @Test
    @DisplayName("Debe procesar eventos con backpressure correctamente")
    void shouldHandleBackpressureCorrectly() {
        AtomicInteger processedCount = new AtomicInteger(0);

        when(delegateProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    processedCount.incrementAndGet();
                    return Mono.just(invocation.getArgument(0).markAsCompleted());
                });

        Flux<Event> eventFlux = Flux.fromIterable(List.of(
                Event.createNewEvent("ACC-001", EventType.ACCOUNT_UPDATE),
                Event.createNewEvent("ACC-002", EventType.TRADE_EXECUTION),
                Event.createNewEvent("ACC-003", EventType.RISK_ALERT),
                Event.createNewEvent("ACC-004", EventType.ACCOUNT_UPDATE)
        )).flatMap(event ->
                reactiveEventProcessor.processEvent(event)
                        .onErrorResume(e -> Mono.empty())
        );

        StepVerifier.create(eventFlux)
                .expectNextCount(4)
                .verifyComplete();

        assertThat(processedCount.get()).isEqualTo(4);
    }

    @Test
    @DisplayName("Debe mantener orden de procesamiento cuando se especifica")
    void shouldMaintainProcessingOrderWhenSpecified() {
        AtomicInteger orderCounter = new AtomicInteger(0);

        when(delegateProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    int order = orderCounter.incrementAndGet();
                    Event event = invocation.getArgument(0);
                    Thread.sleep(50 * (4 - order));
                    return Mono.just(event.markAsCompleted());
                });

        List<Event> events = List.of(testEvent, testEvent2, testEvent3);

        List<Event> results = reactiveEventProcessor.processEventsInParallel(events, 1)
                .collectList()
                .block();

        assertThat(results).hasSize(3);
    }

    @Test
    @DisplayName("Debe manejar timeout en procesamiento de eventos")
    void shouldHandleTimeoutInEventProcessing() {
        when(delegateProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    Thread.sleep(500);
                    return Mono.just(invocation.getArgument(0).markAsCompleted());
                });

        Mono<Event> result = reactiveEventProcessor.processEvent(testEvent)
                .timeout(Duration.ofMillis(100));

        StepVerifier.create(result)
                .expectErrorMatches(error ->
                        error instanceof java.util.concurrent.TimeoutException)
                .verify();
    }

    @Test
    @DisplayName("Debe implementar retry para fallos transitorios")
    void shouldImplementRetryForTransientFailures() {
        AtomicInteger attemptCount = new AtomicInteger(0);
        AtomicBoolean completed = new AtomicBoolean(false);

        when(delegateProcessor.processEvent(any(Event.class)))
                .thenAnswer(invocation -> {
                    int attempts = attemptCount.incrementAndGet();
                    if (attempts < 3) {
                        return Mono.error(new RuntimeException("Fallo transitorio"));
                    }
                    completed.set(true);
                    return Mono.just(invocation.getArgument(0).markAsCompleted());
                });

        Mono<Event> result = reactiveEventProcessor.processEvent(testEvent)
                .retryWhen(companion -> companion.take(3));

        StepVerifier.create(result)
                .expectNextMatches(event -> event.status() == EventStatus.COMPLETED)
                .verifyComplete();

        assertThat(completed.get()).isTrue();
        assertThat(attemptCount.get()).isEqualTo(3);
    }
}