package com.example.exception;

public class OrderTicketException extends RuntimeException {
    public OrderTicketException(String message) {
        super(message);
    }
}
