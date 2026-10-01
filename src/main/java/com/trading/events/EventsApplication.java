package com.trading.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@SpringBootApplication
@EnableAsync
public class EventsApplication {
    private static final int MAX_CONCURRENT_EVENTS = 100;
    private final Semaphore eventSemaphore = new Semaphore(MAX_CONCURRENT_EVENTS);

    public static void main(String[] args) {
        SpringApplication.run(EventsApplication.class, args);
    }

    /**
     * Configura un scheduler dedicado para procesamiento de eventos con tamaño de pool fijo.
     * Esto asegura que los eventos se procesen en hilos dedicados sin bloquear el event loop principal.
     */
    @Bean
    public Executor eventProcessingExecutor() {
        return Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() * 2,
            r -> {
                Thread t = new Thread(r);
                t.setName("event-processing-thread-" + t.getId());
                t.setDaemon(true);
                return t;
            }
        );
    }

    /**
     * Sink reactivo para la emisión de eventos en un modelo publish-subscribe.
     * Permite que múltiples suscriptores consuman eventos de manera concurrente.
     */
    @Bean
    public Sinks.Many<Object> eventSink() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    /**
     * Semáforo para controlar la concurrencia en el procesamiento de eventos.
     * Limita el número de eventos que pueden procesarse simultáneamente para evitar saturar recursos.
     */
    @Bean
    public Semaphore eventSemaphore() {
        return eventSemaphore;
    }

    /**
     * Configura un scheduler para operaciones de I/O no bloqueantes.
     * Ideal para operaciones que involucran llamadas a servicios externos.
     */
    @Bean
    public Scheduler ioScheduler() {
        return Schedulers.newBoundedElastic(
            Runtime.getRuntime().availableProcessors() * 2,
            Integer.MAX_VALUE,
            "io-scheduler"
        );
    }
}