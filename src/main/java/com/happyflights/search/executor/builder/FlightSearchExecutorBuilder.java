package com.happyflights.search.executor.builder;

import com.happyflights.search.executor.FlightSearchExecutor;
import com.happyflights.search.strategy.filter.FlightFilteringStrategy;
import com.happyflights.search.strategy.limit.FlightLimitingStrategy;
import com.happyflights.search.strategy.sort.FlightSortingStrategy;
import com.happyflights.search.strategy.validate.FlightValidatingStrategy;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 A builder class for constructing a {@link FlightSearchExecutor} object with the desired strategies.
 This class follows the builder pattern, allowing for a more readable and flexible way to create {@link FlightSearchExecutor} instances.
 */
public class FlightSearchExecutorBuilder {

    private FlightValidatingStrategy flightValidatingStrategy;
    private final List<FlightFilteringStrategy> flightFilteringStrategies;
    private FlightSortingStrategy flightSortingStrategy;
    private FlightLimitingStrategy flightLimitingStrategy;

    /**
     Constructs a new {@link FlightSearchExecutorBuilder} object.
     */
    public FlightSearchExecutorBuilder() {
        flightFilteringStrategies = new ArrayList<>();
    }

    /**
     Sets the validating strategy for the {@link FlightSearchExecutor}.
     @param flightValidatingStrategy A {@link FlightValidatingStrategy} object to be used for validating flights.
     @return The current {@link FlightSearchExecutorBuilder} instance.
     @throws NullPointerException if the flightValidatingStrategy is null.
     */
    public FlightSearchExecutorBuilder withValidatingStrategy(@NonNull FlightValidatingStrategy flightValidatingStrategy) {
        this.flightValidatingStrategy = flightValidatingStrategy;
        return this;
    }

    /**
     Adds a filtering strategy to the list of flight filtering strategies for the {@link FlightSearchExecutor}.
     @param flightFilteringStrategy A {@link FlightFilteringStrategy} object to be added to the list of flight filtering strategies.
     @return The current {@link FlightSearchExecutorBuilder} instance.
     @throws NullPointerException if the flightFilteringStrategy is null.
     */
    public FlightSearchExecutorBuilder addFilteringStrategy(@NonNull FlightFilteringStrategy flightFilteringStrategy) {
        this.flightFilteringStrategies.add(flightFilteringStrategy);
        return this;
    }

    /**

     Sets the sorting strategy for the {@link FlightSearchExecutor}.
     @param flightSortingStrategy A {@link FlightSortingStrategy} object to be used for sorting flights.
     @return The current {@link FlightSearchExecutorBuilder} instance.
     @throws NullPointerException if the flightSortingStrategy is null.
     */
    public FlightSearchExecutorBuilder withFlightSortingStrategy(@NonNull FlightSortingStrategy flightSortingStrategy) {
        this.flightSortingStrategy = flightSortingStrategy;
        return this;
    }

    /**
     Sets the limiting strategy for the {@link FlightSearchExecutor}.
     @param flightLimitingStrategy A {@link FlightLimitingStrategy} object to be used for limiting the number of flights in the result.
     @return The current {@link FlightSearchExecutorBuilder} instance.
     @throws NullPointerException if the flightLimitingStrategy is null.
     */
    public FlightSearchExecutorBuilder withFlightLimitingStrategy(@NonNull FlightLimitingStrategy flightLimitingStrategy) {
        this.flightLimitingStrategy = flightLimitingStrategy;
        return this;
    }

    /**
     Builds and returns a new {@link FlightSearchExecutor} object with the specified strategies.
     @return A {@link FlightSearchExecutor} object with the specified strategies.
     */
    public FlightSearchExecutor build() {
        return new FlightSearchExecutor(flightValidatingStrategy, flightFilteringStrategies, flightSortingStrategy, flightLimitingStrategy);
    }
}
