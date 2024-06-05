package com.happyflights.search.strategy.validate.impl;

import java.time.Duration;
import java.util.Collection;
import java.util.Objects;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.validate.exception.NegativeFlightDurationException;
import com.happyflights.search.strategy.validate.exception.NegativeFlightPriceException;
import com.happyflights.search.strategy.validate.FlightValidatingStrategy;
import lombok.NonNull;

public class BasicFlightValidation implements FlightValidatingStrategy {
    @Override
    public void validate(@NonNull Collection<FlightSummary> flights) {
        flights.forEach(this::validatePrice);
        flights.forEach(this::validateDates);
        flights.forEach(this::validateFlightDuration);
    }

    private void validatePrice(FlightSummary flight) {
        if (flight.getAveragePriceInUsd() < 0) {
            throw new NegativeFlightPriceException(String.format("Flight has negative average price. Flight: %s", flight));
        }
    }

    private void validateDates(FlightSummary flight) {
        if (Objects.isNull(flight.getDepartureTime()) || Objects.isNull(flight.getArrivalTime())) {
            throw new IllegalArgumentException(String.format("Flight has null departure or arrival time. Flight: %s", flight));
        }
    }

    private void validateFlightDuration(FlightSummary flight) {
        if (Duration.between(flight.getDepartureTime().toInstant(), flight.getArrivalTime().toInstant()).isNegative()) {
            throw new NegativeFlightDurationException(String.format("Flight has negative duration. Flight: %s", flight));
        }
    }
}
