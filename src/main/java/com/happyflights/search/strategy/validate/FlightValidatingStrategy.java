package com.happyflights.search.strategy.validate;

import java.util.Collection;

import com.happyflights.availability.FlightSummary;
import lombok.NonNull;

/**
 An interface for a flight validating strategy that is responsible for validating a collection of {@link FlightSummary} objects.

 Implementations of this interface should define their own validation logic to ensure that the flights
 meet certain requirements or constraints.
 */
public interface FlightValidatingStrategy {
    /**
     Validates the input collection of {@link FlightSummary} objects based on the implemented validation logic.

     @param flights A non-null collection of {@link FlightSummary} objects to be validated.
     @throws NullPointerException if the flights collection is null. */
    void validate(@NonNull Collection<FlightSummary> flights);
}
