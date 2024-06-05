package com.happyflights.search.strategy.sort.impl;

import com.happyflights.availability.FlightSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LengthFlightSortingTest {

    private LengthFlightSorting underTest;

    @BeforeEach
    void setUp() {
        underTest = new LengthFlightSorting();
    }

    @Test
    void testSortWithEmptyCollectionShouldReturnEmptyCollection() {
        Collection<FlightSummary> flights = List.of();

        Collection<FlightSummary> result = underTest.sort(flights);

        assertThat(result).isEmpty();
    }

    @Test
    public void testSortWithSingleFlightShouldReturnSingleFlight() {
        FlightSummary flight = FlightSummary.builder()
                .departureTime(Date.from(Instant.parse("2022-01-01T10:00:00Z")))
                .arrivalTime(Date.from(Instant.parse("2022-01-01T12:00:00Z")))
                .build();
        Collection<FlightSummary> flights = List.of(flight);

        Collection<FlightSummary> result = underTest.sort(flights);

        assertThat(result).containsExactly(flight);
    }

    @Test
    public void testSortWithMultipleFlightsShouldReturnSortedFlights() {
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

        assertThat(result).containsExactly(flight2, flight1, flight3);
    }

    @Test
    public void testSortWithFlightsHavingSameLengthShouldReturnOriginalOrder() {
        FlightSummary flight1 = FlightSummary.builder()
                .departureTime(Date.from(Instant.parse("2022-01-01T10:00:00Z")))
                .arrivalTime(Date.from(Instant.parse("2022-01-01T12:00:00Z")))
                .build();
        FlightSummary flight2 = FlightSummary.builder()
                .departureTime(Date.from(Instant.parse("2022-01-01T08:00:00Z")))
                .arrivalTime(Date.from(Instant.parse("2022-01-01T10:00:00Z")))
                .build();
        Collection<FlightSummary> flights = List.of(flight1, flight2);

        Collection<FlightSummary> result = underTest.sort(flights);

        assertThat(result).containsExactly(flight1, flight2);
    }

    // TODO: All
    @Test
    public void testSortWithNullCollectionShouldThrowNullPointerException() {
        assertThatThrownBy(() -> underTest.sort(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void testSortWithNullFlightInCollectionShouldThrowNullPointerException() {
        Collection<FlightSummary> flights = List.of(null);

        assertThatThrownBy(() -> underTest.sort(flights))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void testSortWithNullDepartureTimeInFlightShouldThrowNullPointerException() {
        FlightSummary flight = FlightSummary.builder()
                .departureTime(null)
                .arrivalTime(Date.from(Instant.parse("2022-01-01T12:00:00Z")))
                .build();
        Collection<FlightSummary> flights = List.of(flight);

        assertThatThrownBy(() -> underTest.sort(flights))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void testSortWithNullArrivalTimeInFlightShouldThrowNullPointerException() {
        FlightSummary flight = FlightSummary.builder()
                .departureTime(Date.from(Instant.parse("2022-01-01T10:00:00Z")))
                .arrivalTime(null)
                .build();
        Collection<FlightSummary> flights = List.of(flight);

        assertThatThrownBy(() -> underTest.sort(flights))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void testSortWithNegativeFlightLengthShouldThrowIllegalArgumentException() {
        FlightSummary flight = FlightSummary.builder()
                .departureTime(Date.from(Instant.parse("2022-01-01T12:00:00Z")))
                .arrivalTime(Date.from(Instant.parse("2022-01-01T10:00:00Z")))
                .build();
        Collection<FlightSummary> flights = List.of(flight);

        assertThatThrownBy(() -> underTest.sort(flights))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Flight length cannot be negative");
    }
}