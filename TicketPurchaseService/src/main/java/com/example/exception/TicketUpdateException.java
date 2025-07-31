package com.example.exception;

public class TicketUpdateException extends RuntimeException {
    public TicketUpdateException(String message) {
        super(message);
    }
}
