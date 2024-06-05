package com.happyflights.search.strategy.validate.exception;

public class NegativeFlightDurationException extends RuntimeException {
    public NegativeFlightDurationException(String message) {
        super(message);
    }
}
