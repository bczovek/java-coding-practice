package com.happyflights.search.strategy.limit.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.limit.FlightLimitingStrategy;
import lombok.NonNull;

import java.util.Collection;

/**
 A class that implements the {@link FlightLimitingStrategy} interface to return the input collection of {@link FlightSummary}
 objects without applying any limiting.
 This class can be used when no specific limiting is required, and the original collection should be maintained without any modifications.
 */
public class NoOpLimiter implements FlightLimitingStrategy {

    /**
     Returns the input collection of {@link FlightSummary} objects without applying any limiting.

     @param flights A non-null collection of {@link FlightSummary} objects.
     @return The same input collection of {@link FlightSummary} objects without any modifications.
     @throws NullPointerException if the flights collection is null.
     */
    @Override
    public Collection<FlightSummary> limit(@NonNull Collection<FlightSummary> flights) {
        return flights;
    }
}
