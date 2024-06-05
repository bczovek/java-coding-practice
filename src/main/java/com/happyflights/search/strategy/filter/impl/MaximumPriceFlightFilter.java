package com.happyflights.search.strategy.filter.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.filter.FlightFilteringStrategy;
import com.happyflights.search.strategy.filter.exception.InvalidMaximumPriceException;
import lombok.NonNull;

import java.util.Collection;
import java.util.stream.Collectors;

public class MaximumPriceFlightFilter implements FlightFilteringStrategy {

    private final Float maximumPrice;

    public MaximumPriceFlightFilter(float maximumPrice) {
        if (maximumPrice < 1.0f) {
            throw new InvalidMaximumPriceException(String.format("Maximum price must be greater than 0. Value provided: %f", maximumPrice));
        }
        this.maximumPrice = maximumPrice;
    }

    @Override
    public Collection<FlightSummary> filter(@NonNull Collection<FlightSummary> flights) {
        return flights.stream().filter(flight -> flight.getAveragePriceInUsd() < maximumPrice)
                .collect(Collectors.toList());
    }
}
