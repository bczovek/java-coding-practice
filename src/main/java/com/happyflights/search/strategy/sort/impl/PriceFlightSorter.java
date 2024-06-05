package com.happyflights.search.strategy.sort.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.model.FlightSearchCriteria;
import com.happyflights.search.strategy.sort.FlightSortingStrategy;
import lombok.NonNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 A class that implements the {@link FlightSortingStrategy} interface to sort a collection of {@link FlightSummary} objects
 based on their average price.
 The sorting can be done in ascending or descending order, as specified by the {@link FlightSearchCriteria.SortOrder} enum.
 */
public class PriceFlightSorter implements FlightSortingStrategy {

    private final FlightSearchCriteria.SortOrder sortOrder;

    /**
     Constructs a {@link PriceFlightSorter} object with the specified sortOrder.

     @param sortOrder A {@link FlightSearchCriteria.SortOrder} enum value indicating the desired sorting order.
     */
    public PriceFlightSorter(FlightSearchCriteria.SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     Constructs a {@link PriceFlightSorter} object with the default sorting order (ascending).
     */
    public PriceFlightSorter() {
        sortOrder = FlightSearchCriteria.SortOrder.ASCENDING;
    }

    /**
     Sorts a collection of {@link FlightSummary} objects based on their average price in the specified order.

     @param flights A non-null collection of {@link FlightSummary} objects to be sorted.
     @return A new collection of {@link FlightSummary} objects sorted based on their average price.
     @throws NullPointerException if the flights collection is null.
     */
    @Override
    public Collection<FlightSummary> sort(@NonNull Collection<FlightSummary> flights) {
        return flights.stream()
                .sorted(getComparator())
                .collect(Collectors.toList());
    }

    /**
     Returns a {@link Comparator} object that compares {@link FlightSummary} objects based on their average price in the specified order.

     @return A {@link Comparator} object for sorting {@link FlightSummary} objects based on their average price.
     */
    private Comparator<FlightSummary> getComparator() {
        Comparator<FlightSummary> comparator = Comparator.comparing(FlightSummary::getAveragePriceInUsd);
        return FlightSearchCriteria.SortOrder.DESCENDING.equals(sortOrder) ? comparator.reversed() : comparator;
    }
}
