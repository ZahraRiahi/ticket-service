package com.ticketsystem.exceptionHandler;

public class RuleException extends RuntimeException {
    public RuleException(String message) {
        super(message);
    }
}
