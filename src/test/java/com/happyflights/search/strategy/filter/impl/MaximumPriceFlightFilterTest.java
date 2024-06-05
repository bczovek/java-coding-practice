package com.happyflights.search.strategy.filter.impl;

import com.happyflights.availability.FlightSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MaximumPriceFlightFilterTest {

    private MaximumPriceFlightFilter underTest;

    @BeforeEach
    void setUp() {
        underTest = new MaximumPriceFlightFilter(100.0F);
    }

    @Test
    public void testFilterWithEmptyListShouldReturnEmptyCollection() {
        Collection<FlightSummary> flights = Collections.emptyList();

        Collection<FlightSummary> result = underTest.filter(flights);

        assertThat(result).isEmpty();
    }

    @Test
    public void testFilterWithSingleFlightBelowMaxPriceShouldReturnSameFlight() {
        FlightSummary flight = FlightSummary.builder().averagePriceInUsd(50.0F).build();
        Collection<FlightSummary> flights = Collections.singletonList(flight);

        Collection<FlightSummary> result = underTest.filter(flights);

        assertThat(result).containsExactly(flight);
    }

    @Test
    public void testFilterWithSingleFlightAboveMaxPriceShouldReturnEmptyCollection() {
        FlightSummary flight = FlightSummary.builder().averagePriceInUsd(150.0F).build();
        Collection<FlightSummary> flights = Collections.singletonList(flight);

        Collection<FlightSummary> result = underTest.filter(flights);

        assertThat(result).isEmpty();
    }

    @Test
    public void testFilterWithMultipleFlightsMixedPricesShouldReturnFilteredCollection() {
        FlightSummary flight1 = FlightSummary.builder().averagePriceInUsd(50.0F).build();
        FlightSummary flight2 = FlightSummary.builder().averagePriceInUsd(150.0F).build();
        FlightSummary flight3 = FlightSummary.builder().averagePriceInUsd(75.0F).build();
        Collection<FlightSummary> flights = List.of(flight1, flight2, flight3);

        Collection<FlightSummary> result = underTest.filter(flights);

        assertThat(result).containsExactlyInAnyOrder(flight1, flight3);
    }

    @Test
    public void testFilterWithNullShouldThrowException() {
        // TODO
        Collection<FlightSummary> result = underTest.filter(null);

        assertThat(result).isNull();
    }

    @Test
    public void testFilterWithNegativeMaxPriceShouldThrowException() {
        // TODO: Throw exception when negative max result.
        underTest = new MaximumPriceFlightFilter(-10);

        FlightSummary flight1 = FlightSummary.builder().averagePriceInUsd(50.0F).build();
        FlightSummary flight2 = FlightSummary.builder().averagePriceInUsd(150.0F).build();
        List<FlightSummary> flights = List.of(flight1, flight2);

        Collection<FlightSummary> result = underTest.filter(flights);

        assertThat(result).isEmpty();
    }
}