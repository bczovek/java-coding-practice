package com.happyflights.search.strategy.sort.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.sort.FlightSortStrategy;
import com.happyflights.search.strategy.sort.exception.NegativeFlightDurationException;
import lombok.NonNull;

import java.time.Duration;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class LengthFlightSorting implements FlightSortStrategy {
    @Override
    public Collection<FlightSummary> sort(@NonNull Collection<FlightSummary> flights) {
        return flights.stream()
                .peek(this::validateFlight)
                .sorted((f1, f2) -> calculateFlightLength(f1)
                        .compareTo(calculateFlightLength(f2)))
                .collect(Collectors.toList());
    }

    private void validateFlight(FlightSummary flight) {
        if (Objects.isNull(flight.getDepartureTime()) || Objects.isNull(flight.getArrivalTime())) {
            throw new IllegalArgumentException(String.format("Flight has null departure or arrival time. Flight: %s", flight));
        }
        if (calculateFlightLength(flight).isNegative()) {
            throw new NegativeFlightDurationException(String.format("Flight has negative duration. Flight: %s", flight));
        }
    }

    private Duration calculateFlightLength(FlightSummary flight) {
        return Duration.between(flight.getDepartureTime().toInstant(), flight.getArrivalTime().toInstant());
    }
}
