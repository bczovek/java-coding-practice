package com.happyflights.search.strategy.filter.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.filter.FlightFilteringStrategy;

import java.util.Collection;
import java.util.stream.Collectors;

public class CancellableFlightFilter implements FlightFilteringStrategy {

    private final Boolean isCancellable;

    public CancellableFlightFilter(Boolean isCancellable) {
        this.isCancellable = isCancellable;
    }

    @Override
    public Collection<FlightSummary> filter(Collection<FlightSummary> flights) {
        return flights.stream().filter(flight -> isCancellable == flight.isCancellationPossible())
                .collect(Collectors.toList());
    }
}
