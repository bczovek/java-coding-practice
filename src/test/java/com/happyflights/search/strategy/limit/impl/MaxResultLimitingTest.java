package com.happyflights.search.strategy.limit.impl;

import com.happyflights.availability.FlightSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MaxResultLimitingTest {

    private MaxResultLimiting underTest;

    @BeforeEach
    void setUp() {
        underTest = new MaxResultLimiting();
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

        assertThat(result).hasSize(MaxResultLimiting.DEFAULT_MAX_RESULT)
                .containsExactlyInAnyOrder(flight1, flight2, flight3);
    }

    @Test
    void testLimitWithCustomMaxResultShouldReturnLimitedCollection() {
        int customMaxResult = 2;
        underTest = new MaxResultLimiting(customMaxResult);
        FlightSummary flight1 = FlightSummary.builder().build();
        FlightSummary flight2 = FlightSummary.builder().build();
        FlightSummary flight3 = FlightSummary.builder().build();
        FlightSummary flight4 = FlightSummary.builder().build();
        Collection<FlightSummary> flights = List.of(flight1, flight2, flight3, flight4);

        Collection<FlightSummary> result = underTest.limit(flights);

        assertThat(result).hasSize(customMaxResult).containsExactlyInAnyOrder(flight1, flight2);
    }

    @Test
    void testLimitWithZeroMaxResultShouldReturnEmptyCollection() {
        int customMaxResult = 0;
        underTest = new MaxResultLimiting(customMaxResult);
        FlightSummary flight1 = FlightSummary.builder().build();
        FlightSummary flight2 = FlightSummary.builder().build();
        Collection<FlightSummary> flights = List.of(flight1, flight2);

        Collection<FlightSummary> result = underTest.limit(flights);

        assertThat(result).isEmpty();
    }
}