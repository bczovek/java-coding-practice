package com.happyflights.search.strategy.sort.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.model.FlightSearchCriteria;
import com.happyflights.search.strategy.sort.FlightSortingStrategy;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A class that implements the {@link FlightSortingStrategy} interface to sort a collection of {@link FlightSummary} objects based
 * on their flight length (duration).
 * The sorting can be done in ascending or descending order, as specified by the {@link FlightSearchCriteria.SortOrder} enum.
 */
public class LengthFlightSorter implements FlightSortingStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(LengthFlightSorter.class);

    private final FlightSearchCriteria.SortOrder sortOrder;

    /**
     * Constructs a {@link LengthFlightSorter} object with the specified sortOrder. In case of null sortOrder, it defaults to ascending
     * order
     *
     * @param sortOrder A {@link FlightSearchCriteria.SortOrder} enum value indicating the desired sorting order.
     */
    public LengthFlightSorter(FlightSearchCriteria.SortOrder sortOrder) {
        if (Objects.isNull(sortOrder)) {
            this.sortOrder = FlightSearchCriteria.SortOrder.ASCENDING;
        } else {
            this.sortOrder = sortOrder;
        }
    }

    /**
     * Constructs a {@link LengthFlightSorter} object with the default sorting order (ascending).
     */
    public LengthFlightSorter() {
        sortOrder = FlightSearchCriteria.SortOrder.ASCENDING;
    }

    /**
     * Sorts a collection of {@link FlightSummary} objects based on their flight length (duration) in the specified order.
     *
     * @param flights A non-null collection of {@link FlightSummary} objects to be sorted.
     * @return A new collection of {@link FlightSummary} objects sorted based on their flight length.
     * @throws NullPointerException if the flights collection is null.
     */
    @Override
    public Collection<FlightSummary> sort(@NonNull Collection<FlightSummary> flights) {
        LOGGER.info("Sorting flights based on length with order: {}", sortOrder);
        List<FlightSummary> filteredFlights = flights.stream()
                .sorted(getComparator())
                .collect(Collectors.toList());
        LOGGER.info("Flights sorted based on length with order: {}", sortOrder);
        return filteredFlights;
    }

    /**
     * Returns a {@link Comparator} object that compares {@link FlightSummary} objects based on their flight length (duration)
     * in the specified order.
     *
     * @return A {@link Comparator} object for sorting {@link FlightSummary} objects based on their flight length.
     */
    private Comparator<FlightSummary> getComparator() {
        Comparator<FlightSummary> comparator = Comparator.comparing(this::calculateFlightLength);
        return FlightSearchCriteria.SortOrder.DESCENDING.equals(sortOrder) ? comparator.reversed() : comparator;
    }

    /**
     * Calculates the flight length (duration) of the given {@link FlightSummary} object.
     *
     * @param flight A {@link FlightSummary} object whose flight length needs to be calculated.
     * @return A {@link Duration} object representing the flight length of the given flight.
     */
    private Duration calculateFlightLength(FlightSummary flight) {
        return Duration.between(flight.getDepartureTime().toInstant(), flight.getArrivalTime().toInstant());
    }
}
