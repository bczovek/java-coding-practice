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
    Date departureTime;
    /**
     * The expected arrival datetime of the flight.
     */
    Date arrivalTime;
    /**
     * The average price of the seats on this flight.
     */
    float averagePriceInUsd;
    /**
     * Whether cancellation is possible for this flight.
     */
    boolean cancellationPossible;
}
