package com.happyflights.search.strategy.filter.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.model.FlightSearchCriteria;
import com.happyflights.search.strategy.filter.FlightFilteringStrategy;
import lombok.NonNull;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * A class that implements the {@link FlightFilteringStrategy} interface to filter a collection of {@link FlightSummary} objects
 * based on their cancellability status.
 * The filter criteria is specified by the {@link FlightSearchCriteria.CancelCriteria} enum.
 */
public class CancelableFlightFilter implements FlightFilteringStrategy {

    private final FlightSearchCriteria.CancelCriteria cancelCriteria;

    /**
     * Constructs a {@link CancelableFlightFilter} object with the specified cancel criteria.
     *
     * @param cancelCriteria A {@link FlightSearchCriteria.CancelCriteria} enum value indicating the desired cancellability filter criteria.
     * @throws NullPointerException if the cancelCriteria is null.
     */
    public CancelableFlightFilter(@NonNull FlightSearchCriteria.CancelCriteria cancelCriteria) {
        this.cancelCriteria = cancelCriteria;
    }

    /**
     * Filters a collection of {@link FlightSummary} objects based on the specified cancellability criteria.
     *
     * @param flights A non-null collection of {@link FlightSummary} objects to be filtered.
     * @return A new collection of {@link FlightSummary} objects that meet the specified cancellability criteria.
     * @throws NullPointerException if the flights collection is null.
     */
    @Override
    public Collection<FlightSummary> filter(@NonNull Collection<FlightSummary> flights) {
        return flights.stream()
                .filter(this::isSuitableFlight)
                .collect(Collectors.toList());
    }

    /**
     * Determines if the given {@link FlightSummary} object meets the specified cancellability criteria.
     *
     * @param flight A {@link FlightSummary} object to be evaluated.
     * @return A boolean value indicating whether the flight meets the specified cancellability criteria.
     * @throws NullPointerException if the flight is null.
     */
    private boolean isSuitableFlight(@NonNull FlightSummary flight) {
        switch (cancelCriteria) {
            case CANCELABLE -> {
                return flight.isCancellationPossible();
            }
            case NON_CANCELABLE -> {
                return !flight.isCancellationPossible();
            }
            default -> {
                return true;
            }
        }
    }
}
