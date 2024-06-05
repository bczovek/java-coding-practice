package com.happyflights.search.strategy.filter.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.filter.FlightFilteringStrategy;
import com.happyflights.search.strategy.filter.exception.InvalidMaximumPriceException;
import lombok.NonNull;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 A class that implements the {@link FlightFilteringStrategy} interface to filter a collection of {@link FlightSummary} objects
 based on a specified maximum price.
 */
public class MaximumPriceFlightFilter implements FlightFilteringStrategy {

    private final Float maximumPrice;

    /**
     Constructs a {@link MaximumPriceFlightFilter} object with the specified maximum price.
     @param maximumPrice A float value representing the maximum price for a flight to be included in the filtered collection.
     @throws InvalidMaximumPriceException if the provided maximumPrice is less than 1.0.
     */
    public MaximumPriceFlightFilter(float maximumPrice) {
        if (maximumPrice < 1.0f) {
            throw new InvalidMaximumPriceException(String.format("Maximum price must be greater than 0. Value provided: %f", maximumPrice));
        }
        this.maximumPrice = maximumPrice;
    }

    /**
     Filters a collection of {@link FlightSummary} objects based on the specified maximum price.
     @param flights A non-null collection of {@link FlightSummary} objects to be filtered.
     @return A new collection of {@link FlightSummary} objects with average prices less than the specified maximum price.
     @throws NullPointerException if the flights collection is null.
     */
    @Override
    public Collection<FlightSummary> filter(@NonNull Collection<FlightSummary> flights) {
        return flights.stream().filter(flight -> flight.getAveragePriceInUsd() < maximumPrice)
                .collect(Collectors.toList());
    }
}
