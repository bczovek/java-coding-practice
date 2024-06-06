package com.happyflights.availability;

import java.util.Date;

import lombok.Builder;
import lombok.Value;

/**
 * Provides summary of a specific flight.
 */
@Value
@Builder
public class FlightSummary {
    /**
     * The airline code.
     */
    String airlineCode;
    /**
     * The departure datetime of the flight.
     */
    // FIXME: java.util.Date is mutable therefore it violates the immutability provided by the @Value annotation,
    //  use java.time.LocalDateTime instead.
    Date departureTime;
    /**
     * The expected arrival datetime of the flight.
     */
    // FIXME: java.util.Date is mutable therefore it violates the immutability provided by the @Value annotation,
    //  use java.time.LocalDateTime instead.
    Date arrivalTime;
    /**
     * The average price of the seats on this flight.
     */
    // FIXME: float is not recommended for monetary values. BigDecimal should be used.
    float averagePriceInUsd;
    /**
     * Whether cancellation is possible for this flight.
     */
    boolean cancellationPossible;
}
