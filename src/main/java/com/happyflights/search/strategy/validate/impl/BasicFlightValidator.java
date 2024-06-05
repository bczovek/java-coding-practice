package com.happyflights.search.strategy.validate.impl;

import java.time.Duration;
import java.util.Collection;
import java.util.Objects;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.validate.exception.NegativeFlightDurationException;
import com.happyflights.search.strategy.validate.exception.NegativeFlightPriceException;
import com.happyflights.search.strategy.validate.FlightValidatingStrategy;
import lombok.NonNull;

/**
 A class that implements the {@link FlightValidatingStrategy} interface to provide basic validation for a collection
 of {@link FlightSummary} objects.
 This class performs the following validations:
 <ul>
 <li>Validates if the flight has a non-negative average price.</li>
 <li>Validates if the flight has non-null departure and arrival times.</li>
 <li>Validates if the flight has a non-negative duration.</li>
 </ul>
 */
public class BasicFlightValidator implements FlightValidatingStrategy {

    /**
     * Validates a collection of {@link FlightSummary} objects by performing the basic validations mentioned above.
     *
     * @param flights A non-null collection of {@link FlightSummary} objects to be validated.
     * @throws NullPointerException if the flights collection is null.
     */
    @Override
    public void validate(@NonNull Collection<FlightSummary> flights) {
        flights.forEach(this::validatePrice);
        flights.forEach(this::validateDates);
        flights.forEach(this::validateFlightDuration);
    }

    /**
     * Validates if the given {@link FlightSummary} object has a non-negative average price.
     *
     * @param flight A {@link FlightSummary} object to be validated.
     * @throws NegativeFlightPriceException if the flight has a negative average price.
     */
    private void validatePrice(FlightSummary flight) {
        if (flight.getAveragePriceInUsd() < 0) {
            throw new NegativeFlightPriceException(String.format("Flight has negative average price. Flight: %s", flight));
        }
    }

    /**
     * Validates if the given {@link FlightSummary} object has non-null departure or arrival time.
     *
     * @param flight A {@link FlightSummary} object to be validated.
     * @throws IllegalArgumentException if the flight has null departure or arrival time.
     */
    private void validateDates(FlightSummary flight) {
        if (Objects.isNull(flight.getDepartureTime()) || Objects.isNull(flight.getArrivalTime())) {
            throw new IllegalArgumentException(String.format("Flight has null departure or arrival time. Flight: %s", flight));
        }
    }

    /**
     * Validates if the given {@link FlightSummary} object has a non-negative flight duration.
     *
     * @param flight A {@link FlightSummary} object to be validated.
     * @throws NegativeFlightDurationException if the flight has a negative duration.
     */
    private void validateFlightDuration(FlightSummary flight) {
        if (Duration.between(flight.getDepartureTime().toInstant(), flight.getArrivalTime().toInstant()).isNegative()) {
            throw new NegativeFlightDurationException(String.format("Flight has negative duration. Flight: %s", flight));
        }
    }
}
