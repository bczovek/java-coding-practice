package com.happyflights.search.strategy.sort;

import com.happyflights.availability.FlightSummary;

import java.util.Collection;

public interface FlightSortingStrategy {
    Collection<FlightSummary> sort(Collection<FlightSummary> flights);
}
