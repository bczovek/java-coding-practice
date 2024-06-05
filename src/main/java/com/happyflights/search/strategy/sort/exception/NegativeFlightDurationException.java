package com.happyflights.search.strategy.sort.exception;

public class NegativeFlightDurationException extends RuntimeException {
    public NegativeFlightDurationException(String message) {
        super(message);
    }
}
