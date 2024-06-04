package com.happyflights.search.strategy.limit.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.limit.FlightLimitingStrategy;

import java.util.Collection;

public class NoOpLimiting implements FlightLimitingStrategy {
    @Override
    public Collection<FlightSummary> limit(Collection<FlightSummary> flights) {
        return flights;
    }
}
