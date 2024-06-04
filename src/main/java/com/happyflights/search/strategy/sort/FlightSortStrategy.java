package com.happyflights.search.strategy.sort;

import com.happyflights.availability.FlightSummary;

import java.util.Collection;

public interface FlightSortStrategy {

    Collection<FlightSummary> sort(Collection<FlightSummary> flights);

}
