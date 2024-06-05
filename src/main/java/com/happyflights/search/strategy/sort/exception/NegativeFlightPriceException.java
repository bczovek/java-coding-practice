package com.happyflights.search.strategy.sort.exception;

public class NegativeFlightPriceException extends RuntimeException {
    public NegativeFlightPriceException(String message) {
        super(message);
    }
}
