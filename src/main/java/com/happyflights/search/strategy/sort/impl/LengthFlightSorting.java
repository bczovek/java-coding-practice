package com.happyflights.search.strategy.sort.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.sort.FlightSortStrategy;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class LengthFlightSorting implements FlightSortStrategy {
    @Override
    public Collection<FlightSummary> sort(Collection<FlightSummary> flights) {
        return flights.stream()
                .sorted((f1, f2) -> calculateFlightLength(f1.getDepartureTime(), f1.getArrivalTime())
                        .compareTo(calculateFlightLength(f2.getDepartureTime(), f2.getArrivalTime())))
                .collect(Collectors.toList());
    }

    private Duration calculateFlightLength(Date departureDate, Date arrivalDate) {
        return Duration.between(departureDate.toInstant(), arrivalDate.toInstant());
    }
}
