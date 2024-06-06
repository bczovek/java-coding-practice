package com.happyflights.search.strategy.limit.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.limit.FlightLimitingStrategy;
import com.happyflights.search.strategy.limit.exception.InvalidMaxResultLimitException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class that implements the {@link FlightLimitingStrategy} interface to limit the number of {@link FlightSummary} objects
 * in a collection based on a specified maximum result count.
 * If no maximum result count is provided, a default value of 3 is used.
 */
@Getter
public class MaxResultLimiter implements FlightLimitingStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaxResultLimiter.class);
    public static final int DEFAULT_MAX_RESULT = 3;

    private final Integer maxResult;

    /**
     * Constructs a {@link MaxResultLimiter} object with the specified maximum result count.
     *
     * @param maxResult An integer value representing the maximum number of results to be included in the limited collection.
     * @throws InvalidMaxResultLimitException if the provided maxResult is less than 1.
     */
    public MaxResultLimiter(Integer maxResult) {
        if (maxResult < 1) {
            throw new InvalidMaxResultLimitException(String.format("Number of maximum results has to be positive. Value provided: %d",
                    maxResult));
        }
        this.maxResult = maxResult;
    }

    /**
     * Constructs a {@link MaxResultLimiter} object with the default maximum result count (3).
     */
    public MaxResultLimiter() {
        maxResult = DEFAULT_MAX_RESULT;
    }

    /**
     * Limits the input collection of {@link FlightSummary} objects based on the specified maximum result count.
     *
     * @param flights A non-null collection of {@link FlightSummary} objects.
     * @return A new collection of {@link FlightSummary} objects with the size limited to the specified maximum result count.
     * @throws NullPointerException if the flights collection is null.
     */
    @Override
    public Collection<FlightSummary> limit(Collection<FlightSummary> flights) {
        LOGGER.info("Limiting number of flights. Limit: {}", maxResult);
        List<FlightSummary> limitedFlights = flights.stream()
                .limit(maxResult)
                .collect(Collectors.toList());
        LOGGER.info("Limited number of flights to {} from {}", limitedFlights.size(), flights.size());
        return limitedFlights;
    }
}
