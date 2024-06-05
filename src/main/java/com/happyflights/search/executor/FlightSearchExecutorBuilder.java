package com.happyflights.search.executor;

import com.happyflights.search.strategy.filter.FlightFilteringStrategy;
import com.happyflights.search.strategy.limit.FlightLimitingStrategy;
import com.happyflights.search.strategy.sort.FlightSortStrategy;
import com.happyflights.search.strategy.validate.FlightValidationStrategy;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class FlightSearchExecutorBuilder {

    private FlightValidationStrategy flightValidationStrategy;
    private final List<FlightFilteringStrategy> flightFilteringStrategies;
    private FlightSortStrategy flightSortStrategy;
    private FlightLimitingStrategy flightLimitingStrategy;

    public FlightSearchExecutorBuilder() {
        flightFilteringStrategies = new ArrayList<>();
    }

    public FlightSearchExecutorBuilder withValidationStrategy(@NonNull FlightValidationStrategy flightValidationStrategy) {
        this.flightValidationStrategy = flightValidationStrategy;
        return this;
    }

    public FlightSearchExecutorBuilder addFilteringStrategy(@NonNull FlightFilteringStrategy flightFilteringStrategy) {
        this.flightFilteringStrategies.add(flightFilteringStrategy);
        return this;
    }

    public FlightSearchExecutorBuilder withFlightSortStrategy(@NonNull FlightSortStrategy flightSortStrategy) {
        this.flightSortStrategy = flightSortStrategy;
        return this;
    }

    public FlightSearchExecutorBuilder withFlightLimitingStrategy(@NonNull FlightLimitingStrategy flightLimitingStrategy) {
        this.flightLimitingStrategy = flightLimitingStrategy;
        return this;
    }

    public FlightSearchExecutor build() {
        return new FlightSearchExecutor(flightValidationStrategy, flightFilteringStrategies, flightSortStrategy, flightLimitingStrategy);
    }
}
