package com.happyflights.search.strategy.sort.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.validate.exception.NegativeFlightPriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PriceFlightSortingTest {

    private PriceFlightSorting underTest;

    @BeforeEach
    void setUp() {
        underTest = new PriceFlightSorting();
    }

    @Test
    void testSortWithEmptyCollectionShouldReturnEmptyCollection() {
        Collection<FlightSummary> flights = List.of();

        Collection<FlightSummary> result = underTest.sort(flights);

        assertThat(result).isEmpty();
    }

    @Test
    void testSortWithSingleFlightShouldReturnSingleFlight() {
        FlightSummary flight = FlightSummary.builder()
                .averagePriceInUsd(100.0f)
                .build();
        Collection<FlightSummary> flights = List.of(flight);

        Collection<FlightSummary> result = underTest.sort(flights);

        assertThat(result).containsExactly(flight);
    }

    @Test
    void testSortWithMultipleFlightsShouldReturnSortedFlights() {
        FlightSummary flight1 = FlightSummary.builder()
                .averagePriceInUsd(100.0f)
                .build();
        FlightSummary flight2 = FlightSummary.builder()
                .averagePriceInUsd(80.0f)
                .build();
        FlightSummary flight3 = FlightSummary.builder()
                .averagePriceInUsd(120.0f)
                .build();
        Collection<FlightSummary> flights = List.of(flight1, flight2, flight3);

        Collection<FlightSummary> result = underTest.sort(flights);

        assertThat(result).containsExactly(flight2, flight1, flight3);
    }

    @Test
    void testSortWithFlightsHavingSamePriceShouldReturnOriginalOrder() {
        FlightSummary flight1 = FlightSummary.builder()
                .averagePriceInUsd(100.0f)
                .build();
        FlightSummary flight2 = FlightSummary.builder()
                .averagePriceInUsd(100.0f)
                .build();
        Collection<FlightSummary> flights = List.of(flight1, flight2);

        Collection<FlightSummary> result = underTest.sort(flights);

        assertThat(result).containsExactly(flight1, flight2);
    }

    @Test
    void testSortWithNullCollectionShouldThrowNullPointerException() {
        assertThatNullPointerException().isThrownBy(() -> underTest.sort(null));
    }
}