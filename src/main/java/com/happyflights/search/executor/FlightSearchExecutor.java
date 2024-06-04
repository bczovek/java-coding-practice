package com.happyflights.search.executor;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.filter.FlightFilteringStrategy;
import com.happyflights.search.strategy.limit.FlightLimitingStrategy;
import com.happyflights.search.strategy.sort.FlightSortStrategy;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class FlightSearchExecutor {

    private final List<FlightFilteringStrategy> flightFilteringStrategies;
    private final FlightSortStrategy flightSortStrategy;
    private final FlightLimitingStrategy flightLimitingStrategy;

    FlightSearchExecutor(List<FlightFilteringStrategy> flightFilteringStrategies, FlightSortStrategy flightSortStrategy, FlightLimitingStrategy flightLimitingStrategy) {
        this.flightFilteringStrategies = flightFilteringStrategies;
        this.flightSortStrategy = flightSortStrategy;
        this.flightLimitingStrategy = flightLimitingStrategy;
    }


    public Collection<FlightSummary> execute(Collection<FlightSummary> flightSummaries) {
        Collection<FlightSummary> result = flightSummaries;
        for(FlightFilteringStrategy flightFilteringStrategy : flightFilteringStrategies) {
            result = flightFilteringStrategy.filter(result);
        }
        if (!Objects.isNull(flightSortStrategy)) {
            result = flightSortStrategy.sort(result);
        }
        if (!Objects.isNull(flightLimitingStrategy)) {
            result = flightLimitingStrategy.limit(result);
        }
        return result;
    }
}
