package com.happyflights.search.strategy.filter.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.filter.FlightFilteringStrategy;

import java.util.Collection;
import java.util.stream.Collectors;

public class MaximumPriceFlightFilter implements FlightFilteringStrategy {

    private final Float maximumPrice;

    public MaximumPriceFlightFilter(float maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    @Override
    public Collection<FlightSummary> filter(Collection<FlightSummary> flights) {
        return flights.stream().filter(flight -> flight.getAveragePriceInUsd() < maximumPrice)
                .collect(Collectors.toList());
    }
}
