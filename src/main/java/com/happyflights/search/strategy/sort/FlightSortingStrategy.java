package com.happyflights.search.strategy.sort;

import com.happyflights.availability.FlightSummary;

import java.util.Collection;

/**
 * An interface for a flight sorting strategy that is responsible for sorting a collection
 * of {@link FlightSummary} objects based on certain criteria.
 * Implementations of this interface should define their own sorting logic to order the flights according to the desired criteria.
 */
public interface FlightSortingStrategy {
    /**
     * Sorts the input collection of {@link FlightSummary} objects based on the implemented sorting logic.
     *
     * @param flights A collection of {@link FlightSummary} objects to be sorted.
     * @return A new collection of {@link FlightSummary} objects sorted according to the implemented sorting criteria.
     */
    Collection<FlightSummary> sort(Collection<FlightSummary> flights);
}
