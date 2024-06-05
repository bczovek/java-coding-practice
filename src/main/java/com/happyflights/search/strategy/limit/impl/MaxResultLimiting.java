package com.happyflights.search.strategy.limit.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.limit.FlightLimitingStrategy;
import com.happyflights.search.strategy.limit.exception.InvalidMaxResultLimitException;
import lombok.Getter;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class MaxResultLimiting implements FlightLimitingStrategy {

    public static final int DEFAULT_MAX_RESULT = 3;
    private final Integer maxResult;

    public MaxResultLimiting(Integer maxResult) {
        if (maxResult < 1) {
            throw new InvalidMaxResultLimitException(String.format("Number of maximum results has to be positive. Value provided: %d", maxResult));
        }
        this.maxResult = maxResult;
    }

    public MaxResultLimiting() {
        maxResult = DEFAULT_MAX_RESULT;
    }

    @Override
    public Collection<FlightSummary> limit(Collection<FlightSummary> flights) {
        return flights.stream()
                .limit(maxResult)
                .collect(Collectors.toList());
    }
}
