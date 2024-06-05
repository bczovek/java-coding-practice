package com.happyflights.search.strategy.filter.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.filter.FlightFilteringStrategy;
import lombok.NonNull;

import java.util.Collection;

/**
 * A class that implements the {@link FlightFilteringStrategy} interface to return the input collection of {@link FlightSummary}
 * objects without applying any filtering.
 * This class can be used when no specific filtering is required, and the original collection should be maintained
 * without any modifications.
 */
public class NoOpFlightFilter implements FlightFilteringStrategy {

    /**
     * Returns the input collection of {@link FlightSummary} objects without applying any filtering.
     *
     * @param flights A non-null collection of {@link FlightSummary} objects.
     * @return The same input collection of {@link FlightSummary} objects without any modifications.
     * @throws NullPointerException if the flights collection is null.
     */
    @Override
    public Collection<FlightSummary> filter(@NonNull Collection<FlightSummary> flights) {
        return flights;
    }
}
