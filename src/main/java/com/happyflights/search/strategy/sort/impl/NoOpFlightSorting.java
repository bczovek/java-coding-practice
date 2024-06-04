package com.happyflights.search.strategy.sort.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.sort.FlightSortStrategy;

import java.util.Collection;

public class NoOpFlightSorting implements FlightSortStrategy {
    @Override
    public Collection<FlightSummary> sort(Collection<FlightSummary> flights) {
        return flights;
    }
}
