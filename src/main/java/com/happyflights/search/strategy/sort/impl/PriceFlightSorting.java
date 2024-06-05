package com.happyflights.search.strategy.sort.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.sort.FlightSortStrategy;
import com.happyflights.search.strategy.sort.exception.NegativeFlightPriceException;
import lombok.NonNull;

import java.util.Collection;
import java.util.stream.Collectors;

public class PriceFlightSorting implements FlightSortStrategy {
    @Override
    public Collection<FlightSummary> sort(@NonNull Collection<FlightSummary> flights) {
        return flights.stream()
                .peek(this::validateFlight)
                .sorted((f1, f2) -> Float.compare(f1.getAveragePriceInUsd(), f2.getAveragePriceInUsd()))
                .collect(Collectors.toList());
    }

    private void validateFlight(FlightSummary flight) {
        if (flight.getAveragePriceInUsd() < 0) {
            throw new NegativeFlightPriceException(String.format("Flight has negative average price. Flight: %s", flight));
        }
    }
}
