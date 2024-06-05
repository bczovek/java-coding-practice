package com.happyflights.search.strategy.limit.impl;

import com.happyflights.availability.FlightSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class NoOpLimiterTest {

    private NoOpLimiter underTest;

    @BeforeEach
    void setUp() {
        underTest = new NoOpLimiter();
    }

    @Test
    public void testLimitWithEmptyCollectionShouldReturnEmptyCollection() {
        Collection<FlightSummary> inputFlights = Collections.emptyList();

        Collection<FlightSummary> result = underTest.limit(inputFlights);

        assertThat(result).isEmpty();
    }

    @Test
    public void testLimitWithSingleFlightShouldReturnSingleFlight() {
        FlightSummary flight = FlightSummary.builder().build();
        List<FlightSummary> flights = List.of(flight);

        Collection<FlightSummary> result = underTest.limit(flights);

        assertThat(result).containsExactly(flight);
    }

    @Test
    public void testLimitWithMultipleFlightsShouldReturnAllFlights() {
        FlightSummary flight1 = FlightSummary.builder().build();
        FlightSummary flight2 = FlightSummary.builder().build();
        Collection<FlightSummary> flights = List.of(flight1, flight2);

        Collection<FlightSummary> result = underTest.limit(flights);

        assertThat(result).containsExactlyInAnyOrder(flight1, flight2);
    }

    @Test
    public void testLimitWithNullCollectionShouldThrowNullPointerException() {
        assertThatNullPointerException().isThrownBy(() -> underTest.limit(null));
    }
}