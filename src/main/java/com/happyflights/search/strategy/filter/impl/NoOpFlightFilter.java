package com.happyflights.search.strategy.filter.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.filter.FlightFilteringStrategy;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * A class that implements the {@link FlightFilteringStrategy} interface to return the input collection of {@link FlightSummary}
 * objects without applying any filtering.
 * This class can be used when no specific filtering is required, and the original collection should be maintained
 * without any modifications.
 */
public class NoOpFlightFilter implements FlightFilteringStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoOpFlightFilter.class);

    /**
     * Returns the input collection of {@link FlightSummary} objects without applying any filtering.
     *
     * @param flights A non-null collection of {@link FlightSummary} objects.
     * @return The same input collection of {@link FlightSummary} objects without any modifications.
     * @throws NullPointerException if the flights collection is null.
     */
    @Override
    public Collection<FlightSummary> filter(@NonNull Collection<FlightSummary> flights) {
        LOGGER.info("No filtering applied, returning all {} flights", flights.size());
        return flights;
    }
}
