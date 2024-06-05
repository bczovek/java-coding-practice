package com.happyflights.search.strategy.filter.impl;

import com.happyflights.availability.FlightSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.api.Assertions.*;

class NoOpFlightFilterTest {

    private NoOpFlightFilter underTest;

    @BeforeEach
    void setUp() {
        underTest = new NoOpFlightFilter();
    }

    @Test
    void testFilterWithEmptyCollectionShouldReturnEmptyCollection() {
        Collection<FlightSummary> flights = Collections.emptyList();

        Collection<FlightSummary> result = underTest.filter(flights);

        assertThat(result).isEqualTo(flights);
    }

    @Test
    void testFilterWithSingleFlightShouldReturnSameFlight() {
        FlightSummary flight = FlightSummary.builder().build();
        Collection<FlightSummary> flights = Collections.singletonList(flight);

        Collection<FlightSummary> result = underTest.filter(flights);

        assertThat(result).isEqualTo(flights);
    }

    @Test
    void testFilterWithMultipleFlightsShouldReturnSameFlights() {
        FlightSummary flight1 = FlightSummary.builder().build();
        FlightSummary flight2 = FlightSummary.builder().build();
        FlightSummary flight3 = FlightSummary.builder().build();
        Collection<FlightSummary> flights = List.of(flight1, flight2, flight3);

        Collection<FlightSummary> result = underTest.filter(flights);

        assertThat(result).isEqualTo(flights);
    }

    @Test
    void testFilterWithNullInputShouldThrowException() {
        // TODO
        assertThatNullPointerException().isThrownBy(() -> underTest.filter(null));
    }
}