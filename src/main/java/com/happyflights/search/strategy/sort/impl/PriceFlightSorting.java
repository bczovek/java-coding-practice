package com.happyflights.search.strategy.sort.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.sort.FlightSortStrategy;

import java.util.Collection;
import java.util.stream.Collectors;

public class PriceFlightSorting implements FlightSortStrategy {
    @Override
    public Collection<FlightSummary> sort(Collection<FlightSummary> flights) {
        return flights.stream()
                .sorted((f1, f2) -> Float.compare(f1.getAveragePriceInUsd(), f2.getAveragePriceInUsd()))
                .collect(Collectors.toList());
    }
}
