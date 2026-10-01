package com.trading.events.domain.exception;


import com.trading.events.domain.model.Event;
/**
 * Excepción personalizada para manejar errores durante el procesamiento de eventos.
 * Se lanza cuando ocurre un error que impide completar el procesamiento de un evento.
 */
public class EventProcessingException extends RuntimeException {

    private final String eventId;
    private final String accountId;

    public EventProcessingException(String eventId, String accountId, String message) {
        super(message);
        this.eventId = eventId;
        this.accountId = accountId;
    }

    public EventProcessingException(String eventId, String accountId, String message, Throwable cause) {
        super(message, cause);
        this.eventId = eventId;
        this.accountId = accountId;
    }

    public String getEventId() {
        return eventId;
    }

    public String getAccountId() {
            return accountId;
    }

    /**
     * Crea una excepción para eventos que no pueden ser procesados por problemas de validación.
     */
    public static EventProcessingException forInvalidEvent(String eventId, String accountId, String reason) {
        return new EventProcessingException(
            eventId,
            accountId,
            "Event " + eventId + " for account " + accountId + " is invalid: " + reason
        );
    }

    /**
     * Crea una excepción para eventos que fallan durante el procesamiento.
     */
    public static EventProcessingException forProcessingFailure(String eventId, String accountId, Throwable cause) {
        return new EventProcessingException(
            eventId,
            accountId,
            "Failed to process event " + eventId + " for account " + accountId,
            cause
        );
    }

    /**
     * Crea una excepción para eventos que no pueden actualizar el estado de la cuenta.
     */
    public static EventProcessingException forAccountUpdateFailure(String eventId, String accountId, Throwable cause) {
        return new EventProcessingException(
            eventId,
            accountId,
            "Failed to update account " + accountId + " for event " + eventId,
            cause
        );
    }
}