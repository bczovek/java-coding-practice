package com.happyflights.search.strategy.limit;

import com.happyflights.availability.FlightSummary;

import java.util.Collection;

/**
 * An interface for a flight limiting strategy that is responsible for limiting the number of {@link FlightSummary}
 * objects in a collection based on certain criteria.
 * Implementations of this interface should define their own limiting logic to select a subset of
 * flights according to the desired criteria or limits.
 */
public interface FlightLimitingStrategy {
    /**
     * Limits the input collection of {@link FlightSummary} objects based on the implemented limiting logic.
     *
     * @param flights A collection of {@link FlightSummary} objects to be limited.
     * @return A new collection of {@link FlightSummary} objects limited according to the implemented limiting criteria.
     */
    Collection<FlightSummary> limit(Collection<FlightSummary> flights);
}
