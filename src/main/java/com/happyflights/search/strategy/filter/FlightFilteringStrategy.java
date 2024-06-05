package com.happyflights.search.strategy.filter;

import com.happyflights.availability.FlightSummary;

import java.util.Collection;

/**
 An interface for a flight filtering strategy that is responsible for filtering a collection of {@link FlightSummary}
 objects based on certain criteria.

 Implementations of this interface should define their own filtering logic to select a subset of
 flights that meet the desired criteria or conditions.
 */
public interface FlightFilteringStrategy {
    /**
     Filters the input collection of {@link FlightSummary} objects based on the implemented filtering logic.

     @param flights A collection of {@link FlightSummary} objects to be filtered.
     @return A new collection of {@link FlightSummary} objects that meet the implemented filtering criteria.
     */
    Collection<FlightSummary> filter(Collection<FlightSummary> flights);
}
