package com.happyflights.search.strategy.limit.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.limit.exception.InvalidMaxResultLimitException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MaxResultLimiterTest {

    private MaxResultLimiter underTest;

    @BeforeEach
    void setUp() {
        underTest = new MaxResultLimiter();
    }

    @Test
    void testLimitWithEmptyCollectionShouldReturnEmptyCollection() {
        Collection<FlightSummary> flights = Collections.emptyList();

        Collection<FlightSummary> result = underTest.limit(flights);

        assertThat(result).isEmpty();
    }

    @Test
    void testLimitWithLessThanDefaultMaxResultShouldReturnSameCollection() {
        FlightSummary flight1 = FlightSummary.builder().build();
        FlightSummary flight2 = FlightSummary.builder().build();
        Collection<FlightSummary> flights = List.of(flight1, flight2);

        Collection<FlightSummary> result = underTest.limit(flights);

        assertThat(result).containsExactlyInAnyOrderElementsOf(flights);
    }

    @Test
    void testLimitWithMoreThanDefaultMaxResultShouldReturnLimitedCollection() {
        FlightSummary flight1 = FlightSummary.builder().build();
        FlightSummary flight2 = FlightSummary.builder().build();
        FlightSummary flight3 = FlightSummary.builder().build();
        FlightSummary flight4 = FlightSummary.builder().build();
        Collection<FlightSummary> flights = List.of(flight1, flight2, flight3, flight4);

        Collection<FlightSummary> result = underTest.limit(flights);

        assertThat(result).hasSize(MaxResultLimiter.DEFAULT_MAX_RESULT)
                .containsExactlyInAnyOrder(flight1, flight2, flight3);
    }

    @Test
    void testLimitWithCustomMaxResultShouldReturnLimitedCollection() {
        int customMaxResult = 2;
        underTest = new MaxResultLimiter(customMaxResult);
        FlightSummary flight1 = FlightSummary.builder().build();
        FlightSummary flight2 = FlightSummary.builder().build();
        FlightSummary flight3 = FlightSummary.builder().build();
        FlightSummary flight4 = FlightSummary.builder().build();
        Collection<FlightSummary> flights = List.of(flight1, flight2, flight3, flight4);

        Collection<FlightSummary> result = underTest.limit(flights);

        assertThat(result).hasSize(customMaxResult).containsExactlyInAnyOrder(flight1, flight2);
    }

    @Test
    void testWithZeroMaxResultShouldThrowInvalidMaxResultLimitException() {
        assertThatThrownBy(() -> new MaxResultLimiter(0))
                .isInstanceOf(InvalidMaxResultLimitException.class);
    }

    @Test
    void testWithNegativeMaxResultShouldThrowInvalidMaxResultLimitException() {
        assertThatThrownBy(() -> new MaxResultLimiter(-1))
                .isInstanceOf(InvalidMaxResultLimitException.class);
    }
}