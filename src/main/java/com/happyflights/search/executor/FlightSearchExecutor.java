package com.happyflights.search.executor;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.filter.FlightFilteringStrategy;
import com.happyflights.search.strategy.limit.FlightLimitingStrategy;
import com.happyflights.search.strategy.sort.FlightSortingStrategy;
import com.happyflights.search.strategy.validate.FlightValidatingStrategy;
import lombok.Getter;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A class that executes a flight search by applying a series of strategies to a collection of {@link FlightSummary} objects.
 * The strategies include validation, filtering, sorting, and limiting.
 */
public class FlightSearchExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightSearchExecutor.class);

    @Getter
    private final FlightValidatingStrategy flightValidatingStrategy;
    private final List<FlightFilteringStrategy> flightFilteringStrategies;
    @Getter
    private final FlightSortingStrategy flightSortingStrategy;
    @Getter
    private final FlightLimitingStrategy flightLimitingStrategy;

    /**
     * Constructs a {@link FlightSearchExecutor} object with the specified strategies.
     *
     * @param flightValidatingStrategy  A {@link FlightValidatingStrategy} object for validating flights.
     * @param flightFilteringStrategies A list of {@link FlightFilteringStrategy} objects for filtering flights.
     * @param flightSortingStrategy     A {@link FlightSortingStrategy} object for sorting flights.
     * @param flightLimitingStrategy    A {@link FlightLimitingStrategy} object for limiting the number of flights in the result.
     * @throws NullPointerException if any of the provided strategies is null.
     */
    public FlightSearchExecutor(@NonNull FlightValidatingStrategy flightValidatingStrategy,
                                @NonNull List<FlightFilteringStrategy> flightFilteringStrategies,
                                @NonNull FlightSortingStrategy flightSortingStrategy,
                                @NonNull FlightLimitingStrategy flightLimitingStrategy) {
        this.flightValidatingStrategy = flightValidatingStrategy;
        this.flightFilteringStrategies = flightFilteringStrategies;
        this.flightSortingStrategy = flightSortingStrategy;
        this.flightLimitingStrategy = flightLimitingStrategy;
    }

    /**
     * Executes the flight search by applying the specified strategies to the input collection of {@link FlightSummary} objects.
     *
     * @param flightSummaries A non-null collection of {@link FlightSummary} objects to be processed.
     * @return A new collection of {@link FlightSummary} objects after applying the specified strategies.
     * @throws NullPointerException if the flightSummaries collection is null.
     */
    public Collection<FlightSummary> execute(@NonNull Collection<FlightSummary> flightSummaries) {
        LOGGER.info("Executing flight search with {} flights", flightSummaries.size());
        Collection<FlightSummary> result = new ArrayList<>(flightSummaries);
        flightValidatingStrategy.validate(result);

        for (FlightFilteringStrategy flightFilteringStrategy : flightFilteringStrategies) {
            result = flightFilteringStrategy.filter(result);
        }
        result = flightSortingStrategy.sort(result);
        result = flightLimitingStrategy.limit(result);
        LOGGER.info("Flight search execution completed, returning {} flights", result.size());
        return result;
    }

    /**
     * Returns an unmodifiable list of flight filtering strategies.
     *
     * @return An unmodifiable list of {@link FlightFilteringStrategy} objects.
     */
    public List<FlightFilteringStrategy> getFlightFilteringStrategies() {
        return List.copyOf(flightFilteringStrategies);
    }

}
