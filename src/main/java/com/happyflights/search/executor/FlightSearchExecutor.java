package com.happyflights.search.executor;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.filter.FlightFilteringStrategy;
import com.happyflights.search.strategy.limit.FlightLimitingStrategy;
import com.happyflights.search.strategy.sort.FlightSortStrategy;
import com.happyflights.search.strategy.validate.FlightValidatingStrategy;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FlightSearchExecutor {

    @Getter
    private final FlightValidatingStrategy flightValidatingStrategy;
    private final List<FlightFilteringStrategy> flightFilteringStrategies;
    @Getter
    private final FlightSortStrategy flightSortStrategy;
    @Getter
    private final FlightLimitingStrategy flightLimitingStrategy;

    public FlightSearchExecutor(@NonNull FlightValidatingStrategy flightValidatingStrategy, @NonNull List<FlightFilteringStrategy> flightFilteringStrategies,
                                @NonNull FlightSortStrategy flightSortStrategy, @NonNull FlightLimitingStrategy flightLimitingStrategy) {
        this.flightValidatingStrategy = flightValidatingStrategy;
        this.flightFilteringStrategies = flightFilteringStrategies;
        this.flightSortStrategy = flightSortStrategy;
        this.flightLimitingStrategy = flightLimitingStrategy;
    }


    public Collection<FlightSummary> execute(@NonNull Collection<FlightSummary> flightSummaries) {
        Collection<FlightSummary> result = new ArrayList<>(flightSummaries);
        flightValidatingStrategy.validate(result);
        for (FlightFilteringStrategy flightFilteringStrategy : flightFilteringStrategies) {
            result = flightFilteringStrategy.filter(result);
        }
        result = flightSortStrategy.sort(result);
        result = flightLimitingStrategy.limit(result);
        return result;
    }

    public List<FlightFilteringStrategy> getFlightFilteringStrategies() {
        return List.copyOf(flightFilteringStrategies);
    }

}
