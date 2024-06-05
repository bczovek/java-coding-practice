package com.happyflights.search.strategy.sort.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.sort.FlightSortingStrategy;
import lombok.NonNull;

import java.util.Collection;
import java.util.stream.Collectors;

public class PriceFlightSorter implements FlightSortingStrategy {
    @Override
    public Collection<FlightSummary> sort(@NonNull Collection<FlightSummary> flights) {
        return flights.stream()
                .sorted((f1, f2) -> Float.compare(f1.getAveragePriceInUsd(), f2.getAveragePriceInUsd()))
                .collect(Collectors.toList());
    }
}
