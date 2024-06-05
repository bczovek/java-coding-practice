package com.happyflights.search.strategy.sort.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.validate.exception.NegativeFlightDurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
    void testSortWithMultipleFlightsShouldReturnSortedFlights() {
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
    void testSortWithFlightsHavingSameLengthShouldReturnOriginalOrder() {
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

    @Test
    void testSortWithNullCollectionShouldThrowNullPointerException() {
        assertThatNullPointerException().isThrownBy(() -> underTest.sort(null))
                .withMessage("flights is marked non-null but is null");
    }

    @Test
    void testSortWithNullDepartureTimeInFlightShouldThrowIllegalArgumentException() {
        FlightSummary flight = FlightSummary.builder()
                .departureTime(null)
                .arrivalTime(Date.from(Instant.parse("2022-01-01T12:00:00Z")))
                .build();
        Collection<FlightSummary> flights = List.of(flight);

        assertThatIllegalArgumentException().isThrownBy(() -> underTest.sort(flights));
    }

    @Test
    void testSortWithNullArrivalTimeInFlightShouldThrowIllegalArgumentException() {
        FlightSummary flight = FlightSummary.builder()
                .departureTime(Date.from(Instant.parse("2022-01-01T10:00:00Z")))
                .arrivalTime(null)
                .build();
        Collection<FlightSummary> flights = List.of(flight);

        assertThatIllegalArgumentException().isThrownBy(() -> underTest.sort(flights));
    }

    @Test
    void testSortWithNegativeFlightLengthShouldThrowNegativeFlightDurationException() {
        FlightSummary flight = FlightSummary.builder()
                .departureTime(Date.from(Instant.parse("2022-01-01T12:00:00Z")))
                .arrivalTime(Date.from(Instant.parse("2022-01-01T10:00:00Z")))
                .build();
        Collection<FlightSummary> flights = List.of(flight);

        assertThatThrownBy(() -> underTest.sort(flights))
                .isInstanceOf(NegativeFlightDurationException.class)
                .hasMessage(String.format("Flight has negative duration. Flight: %s", flight));
    }
}