package com.happyflights.search.strategy.filter;

import com.happyflights.availability.FlightSummary;

import java.util.Collection;

public interface FlightFilteringStrategy {

    Collection<FlightSummary> filter(Collection<FlightSummary> flights);

}
