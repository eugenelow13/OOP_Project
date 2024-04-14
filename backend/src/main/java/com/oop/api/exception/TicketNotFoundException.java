package com.oop.api.exception;

/**
 * Exception thrown when a ticket is not found.
 */
public class TicketNotFoundException extends RuntimeException {
    /**
     * Constructs a new TicketNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public TicketNotFoundException(String message) {
        super(message);
    }
}
