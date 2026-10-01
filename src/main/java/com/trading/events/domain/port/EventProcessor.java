package com.trading.events.domain.port;

import com.trading.events.domain.model.Event;
import reactor.core.publisher.Mono;

/**
 * Puerto que define el contrato para procesar eventos de trading de manera concurrente.
 * Las implementaciones deben manejar la concurrencia y paralelismo de forma eficiente.
 */
public interface EventProcessor {

    /**
     * Procesa un evento de trading de forma asíncrona y concurrente.
     *
     * @param event Evento a procesar
     * @return Mono que emite el evento procesado o un error si ocurre alguna falla
     */
    Mono<Event> processEvent(Event event);

    /**
     * Procesa múltiples eventos en paralelo, limitando el grado de concurrencia.
     *
     * @param events Flujo de eventos a procesar
     * @param maxConcurrency Número máximo de eventos a procesar concurrentemente
     * @return Flujo de eventos procesados
     */
    default Mono<Void> processEventsInParallel(Iterable<Event> events, int maxConcurrency) {
        return Mono.fromRunnable(() -> {
            if (maxConcurrency <= 0) {
                throw new IllegalArgumentException("Max concurrency must be positive");
            }
        }).thenMany(reactor.core.publisher.Flux.fromIterable(events)
            .flatMap(this::processEvent, maxConcurrency)
        ).then();
    }

    /**
     * Verifica si el procesador está disponible para aceptar nuevos eventos.
     *
     * @return true si el procesador puede aceptar eventos, false en caso contrario
     */
    default boolean isAvailable() {
        return true;
    }
}