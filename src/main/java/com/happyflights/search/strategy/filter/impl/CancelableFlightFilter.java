package com.happyflights.search.strategy.filter.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.model.FlightSearchCriteria;
import com.happyflights.search.strategy.filter.FlightFilteringStrategy;

import java.util.Collection;
import java.util.stream.Collectors;

public class CancelableFlightFilter implements FlightFilteringStrategy {

    private final FlightSearchCriteria.CancelCriteria cancelCriteria;

    public CancelableFlightFilter(FlightSearchCriteria.CancelCriteria cancelCriteria) {
        this.cancelCriteria = cancelCriteria;
    }

    @Override
    public Collection<FlightSummary> filter(Collection<FlightSummary> flights) {
        return flights.stream()
                .filter(this::isSuitableFlight)
                .collect(Collectors.toList());
    }

    private boolean isSuitableFlight(FlightSummary flight) {
        switch (cancelCriteria) {
            case CANCELABLE -> {
                return flight.isCancellationPossible() == true;
            }
            case NON_CANCELABLE -> {
                return flight.isCancellationPossible() == false;
            }
        }
        return true;
    }
}
