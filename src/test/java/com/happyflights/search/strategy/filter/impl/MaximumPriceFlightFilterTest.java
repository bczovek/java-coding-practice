package com.happyflights.search.strategy.filter.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.filter.exception.InvalidMaximumPriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MaximumPriceFlightFilterTest {

    private MaximumPriceFlightFilter underTest;

    @BeforeEach
    void setUp() {
        underTest = new MaximumPriceFlightFilter(100.0f);
    }

    @Test
    void testFilterWithEmptyListShouldReturnEmptyCollection() {
        Collection<FlightSummary> flights = Collections.emptyList();

        Collection<FlightSummary> result = underTest.filter(flights);

        assertThat(result).isEmpty();
    }

    @Test
    void testFilterWithSingleFlightBelowMaxPriceShouldReturnSameFlight() {
        FlightSummary flight = FlightSummary.builder().averagePriceInUsd(50.0f).build();
        Collection<FlightSummary> flights = Collections.singletonList(flight);

        Collection<FlightSummary> result = underTest.filter(flights);

        assertThat(result).containsExactly(flight);
    }

    @Test
    void testFilterWithSingleFlightAboveMaxPriceShouldReturnEmptyCollection() {
        FlightSummary flight = FlightSummary.builder().averagePriceInUsd(150.0f).build();
        Collection<FlightSummary> flights = Collections.singletonList(flight);

        Collection<FlightSummary> result = underTest.filter(flights);

        assertThat(result).isEmpty();
    }

    @Test
    void testFilterWithMultipleFlightsMixedPricesShouldReturnFilteredCollection() {
        FlightSummary flight1 = FlightSummary.builder().averagePriceInUsd(50.0f).build();
        FlightSummary flight2 = FlightSummary.builder().averagePriceInUsd(150.0f).build();
        FlightSummary flight3 = FlightSummary.builder().averagePriceInUsd(75.0f).build();
        Collection<FlightSummary> flights = List.of(flight1, flight2, flight3);

        Collection<FlightSummary> result = underTest.filter(flights);

        assertThat(result).containsExactlyInAnyOrder(flight1, flight3);
    }

    @Test
    void testFilterWithNullShouldThrowNullPointerException() {
        assertThatNullPointerException().isThrownBy(() -> underTest.filter(null))
                .withMessage("flights is marked non-null but is null");
    }

    @Test
    void testFilterWithNegativeMaxPriceShouldThrowInvalidMaximumPriceException() {
        assertThatThrownBy(() -> new MaximumPriceFlightFilter(-10f))
                .isInstanceOf(InvalidMaximumPriceException.class)
                .hasMessage("Maximum price must be greater than 0. Value provided: -10.000000");
    }
}