package com.happyflights.search.strategy.limit;

import com.happyflights.availability.FlightSummary;

import java.util.Collection;

public interface FlightLimitingStrategy {
    Collection<FlightSummary> limit(Collection<FlightSummary> flights);

}
