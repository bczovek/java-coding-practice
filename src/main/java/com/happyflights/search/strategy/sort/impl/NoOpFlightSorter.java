package com.happyflights.search.strategy.sort.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.sort.FlightSortingStrategy;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * A class that implements the {@link FlightSortingStrategy} interface to return the input collection of {@link FlightSummary}
 * objects without any sorting.
 * This class can be used when no specific sorting is required, and the original order of the collection should be maintained.
 */
public class NoOpFlightSorter implements FlightSortingStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoOpFlightSorter.class);

    /**
     * Returns the input collection of {@link FlightSummary} objects without applying any sorting.
     *
     * @param flights A non-null collection of {@link FlightSummary} objects.
     * @return The same input collection of {@link FlightSummary} objects without any modifications.
     * @throws NullPointerException if the flights collection is null.
     */
    @Override
    public Collection<FlightSummary> sort(@NonNull Collection<FlightSummary> flights) {
        LOGGER.info("No sorting applied");
        return flights;
    }
}
