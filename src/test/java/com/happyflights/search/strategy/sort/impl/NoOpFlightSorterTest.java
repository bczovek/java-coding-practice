package com.happyflights.search.strategy.sort.impl;

import com.happyflights.availability.FlightSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class NoOpFlightSorterTest {
    private NoOpFlightSorter underTest;

    @BeforeEach
    void setUp() {
        underTest = new NoOpFlightSorter();
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
                .departureTime(Date.from(Instant.parse("2022-01-01T10:00:00Z")))
                .arrivalTime(Date.from(Instant.parse("2022-01-01T12:00:00Z")))
                .build();
        Collection<FlightSummary> flights = List.of(flight);

        Collection<FlightSummary> result = underTest.sort(flights);

        assertThat(result).containsExactly(flight);
    }

    @Test
    void testSortWithMultipleFlightsShouldReturnUnchangedOrder() {
        FlightSummary flight1 = FlightSummary.builder()
                .departureTime(Date.from(Instant.parse("2022-01-01T10:00:00Z")))
                .arrivalTime(Date.from(Instant.parse("2022-01-01T12:00:00Z")))
                .build();
        FlightSummary flight2 = FlightSummary.builder()
                .departureTime(Date.from(Instant.parse("2022-01-01T10:00:00Z")))
                .arrivalTime(Date.from(Instant.parse("2022-01-01T11:00:00Z")))
                .build();
        FlightSummary flight3 = FlightSummary.builder()
                .departureTime(Date.from(Instant.parse("2022-01-01T10:00:00Z")))
                .arrivalTime(Date.from(Instant.parse("2022-01-01T13:00:00Z")))
                .build();
        Collection<FlightSummary> flights = List.of(flight1, flight2, flight3);

        Collection<FlightSummary> result = underTest.sort(flights);

        assertThat(result).containsExactly(flight1, flight2, flight3);
    }

    @Test
    public void testSortWithNullCollectionShouldThrowNullPointerException() {
        assertThatNullPointerException().isThrownBy(() -> underTest.sort(null));
    }
}