package com.happyflights.search.strategy.sort;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.model.FlightSearchCriteria;

import java.util.Collection;
import java.util.List;

public interface FlightSortStrategy {

    Collection<FlightSummary> sort(Collection<FlightSummary> flights);

}
