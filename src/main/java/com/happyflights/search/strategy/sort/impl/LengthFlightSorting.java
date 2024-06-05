package com.happyflights.search.strategy.sort.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.sort.FlightSortStrategy;
import lombok.NonNull;

import java.time.Duration;
import java.util.Collection;
import java.util.stream.Collectors;

public class LengthFlightSorting implements FlightSortStrategy {
    @Override
    public Collection<FlightSummary> sort(@NonNull Collection<FlightSummary> flights) {
        return flights.stream()
                .sorted((f1, f2) -> calculateFlightLength(f1)
                        .compareTo(calculateFlightLength(f2)))
                .collect(Collectors.toList());
    }

    private Duration calculateFlightLength(FlightSummary flight) {
        return Duration.between(flight.getDepartureTime().toInstant(), flight.getArrivalTime().toInstant());
    }
}
