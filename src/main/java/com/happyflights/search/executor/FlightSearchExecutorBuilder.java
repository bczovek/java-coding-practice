package com.happyflights.search.executor;

import com.happyflights.search.strategy.filter.FlightFilteringStrategy;
import com.happyflights.search.strategy.filter.impl.NoOpFlightFilter;
import com.happyflights.search.strategy.limit.FlightLimitingStrategy;
import com.happyflights.search.strategy.limit.impl.NoOpLimiting;
import com.happyflights.search.strategy.sort.FlightSortStrategy;
import com.happyflights.search.strategy.sort.impl.NoOpFlightSorting;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FlightSearchExecutorBuilder {

    private final List<FlightFilteringStrategy> flightFilteringStrategies;
    private FlightSortStrategy flightSortStrategy;
    private FlightLimitingStrategy flightLimitingStrategy;

    public FlightSearchExecutorBuilder() {
        flightFilteringStrategies = new ArrayList<>();
    }

    public FlightSearchExecutorBuilder addFilteringStrategy(FlightFilteringStrategy flightFilteringStrategy) {
        this.flightFilteringStrategies.add(flightFilteringStrategy);
        return this;
    }

    public FlightSearchExecutorBuilder withFlightSortStrategy(FlightSortStrategy flightSortStrategy) {
        this.flightSortStrategy = flightSortStrategy;
        return this;
    }

    public FlightSearchExecutorBuilder withFlightLimitingStrategy(FlightLimitingStrategy flightLimitingStrategy) {
        this.flightLimitingStrategy = flightLimitingStrategy;
        return this;
    }

    public FlightSearchExecutor build() {
        if(flightFilteringStrategies.isEmpty()) {
            flightFilteringStrategies.add(new NoOpFlightFilter());
        }
        if(Objects.isNull(flightSortStrategy)) {
            flightSortStrategy = new NoOpFlightSorting();
        }
        if(Objects.isNull(flightLimitingStrategy)) {
            flightLimitingStrategy = new NoOpLimiting();
        }

        return new FlightSearchExecutor(flightFilteringStrategies, flightSortStrategy, flightLimitingStrategy);
    }
}
