package com.happyflights.search.strategy.limit.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.limit.FlightLimitingStrategy;

import java.util.Collection;
import java.util.stream.Collectors;

public class MaxResultLimiting implements FlightLimitingStrategy {

    private final Integer maxResult;
    public MaxResultLimiting(Integer maxResult) {
        this.maxResult = maxResult;
    }

    @Override
    public Collection<FlightSummary> limit(Collection<FlightSummary> flights) {
        return flights.stream()
                .limit(maxResult)
                .collect(Collectors.toList());
    }
}
