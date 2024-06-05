package com.happyflights.search.strategy.sort.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.model.FlightSearchCriteria;
import com.happyflights.search.strategy.sort.FlightSortingStrategy;
import lombok.NonNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public class PriceFlightSorter implements FlightSortingStrategy {

    private final FlightSearchCriteria.SortOrder sortOrder;

    public PriceFlightSorter(FlightSearchCriteria.SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public PriceFlightSorter() {
        sortOrder = FlightSearchCriteria.SortOrder.ASCENDING;
    }

    @Override
    public Collection<FlightSummary> sort(@NonNull Collection<FlightSummary> flights) {
        return flights.stream()
                .sorted(getComparator())
                .collect(Collectors.toList());
    }

    private Comparator<FlightSummary> getComparator() {
        Comparator<FlightSummary> comparator = Comparator.comparing(FlightSummary::getAveragePriceInUsd);
        return FlightSearchCriteria.SortOrder.DESCENDING.equals(sortOrder) ? comparator.reversed() : comparator;
    }
}
