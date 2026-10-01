package com.trading.events.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa un evento de trading que debe ser procesado concurrentemente.
 * Cada evento contiene información sobre una operación financiera que afecta
 * cuentas y balances en tiempo real.
 */
public record Event(
    UUID eventId,
    String accountId,
    EventType eventType,
    BigDecimal amount,
    LocalDateTime timestamp,
    EventStatus status,
    String marketSource) {

    /**
     * Tipos de eventos soportados en el sistema.
     */
    public enum EventType {
        DEPOSIT,
        WITHDRAWAL,
        TRADE_EXECUTION,
        FEE_APPLIED,
        INTEREST_CALCULATION
    }

    /**
     * Estados posibles de un evento durante su procesamiento.
     */
    public enum EventStatus {
        RECEIVED,
        PROCESSING,
        COMPLETED,
        FAILED
    }

    /**
     * Crea un nuevo evento con el estado inicial RECEIVED.
     */
    public static Event createNewEvent(String accountId, EventType eventType,
                                     BigDecimal amount, String marketSource) {
        return new Event(
            UUID.randomUUID(),
            accountId,
            eventType,
            amount,
            LocalDateTime.now(),
            EventStatus.RECEIVED,
            marketSource
        );
    }

    /**
     * Marca el evento como PROCESSING si está en estado RECEIVED.
     * @return Nuevo evento con estado actualizado o lanza excepción si no es válido transicionar.
     * @throws IllegalStateException si el evento no está en estado RECEIVED
     */
    public Event markAsProcessing() {
        if (this.status != EventStatus.RECEIVED) {
            throw new IllegalStateException("Event cannot transition to PROCESSING from " + this.status);
        }
        return new Event(
            this.eventId,
            this.accountId,
            this.eventType,
            this.amount,
            this.timestamp,
            EventStatus.PROCESSING,
            this.marketSource
        );
    }

    /**
     * Marca el evento como COMPLETED si está en estado PROCESSING.
     * @return Nuevo evento con estado actualizado
     * @throws IllegalStateException si el evento no está en estado PROCESSING
     */
    public Event markAsCompleted() {
        if (this.status != EventStatus.PROCESSING) {
            throw new IllegalStateException("Event cannot transition to COMPLETED from " + this.status);
        }
        return new Event(
            this.eventId,
            this.accountId,
            this.eventType,
            this.amount,
            this.timestamp,
            EventStatus.COMPLETED,
            this.marketSource
        );
    }

    /**
     * Marca el evento como FAILED si está en estado PROCESSING.
     * @return Nuevo evento con estado actualizado
     * @throws IllegalStateException si el evento no está en estado PROCESSING
     */
    public Event markAsFailed() {
        if (this.status != EventStatus.PROCESSING) {
            throw new IllegalStateException("Event cannot transition to FAILED from " + this.status);
        }
        return new Event(
            this.eventId,
            this.accountId,
            this.eventType,
            this.amount,
            this.timestamp,
            EventStatus.FAILED,
            this.marketSource
        );
    }

    /**
     * Valida que el evento tenga datos consistentes para procesamiento.
     * @throws IllegalArgumentException si algún campo es inválido
     */
    public void validate() {
        if (accountId == null || accountId.isBlank()) {
            throw new IllegalArgumentException("Account ID cannot be null or empty");
        }
        if (eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (marketSource == null || marketSource.isBlank()) {
            throw new IllegalArgumentException("Market source cannot be null or empty");
        }
    }
}