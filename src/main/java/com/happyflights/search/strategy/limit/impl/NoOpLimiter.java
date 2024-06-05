package com.happyflights.search.strategy.limit.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.limit.FlightLimitingStrategy;
import lombok.NonNull;

import java.util.Collection;

public class NoOpLimiter implements FlightLimitingStrategy {
    @Override
    public Collection<FlightSummary> limit(@NonNull Collection<FlightSummary> flights) {
        return flights;
    }
}
