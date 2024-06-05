package com.happyflights.search.strategy.validate.exception;

public class NegativeFlightPriceException extends RuntimeException {
    public NegativeFlightPriceException(String message) {
        super(message);
    }
}
