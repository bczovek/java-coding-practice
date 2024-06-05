package com.happyflights.search.strategy.filter.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.model.FlightSearchCriteria;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CancelableFlightFilterTest {

    private final FlightSummary cancelableFlight = FlightSummary.builder().cancellationPossible(true).build();
    private final FlightSummary nonCancelableFlight = FlightSummary.builder().cancellationPossible(false).build();

    @Nested
    @DisplayName("Test cases for CANCELABLE criteria")
    class CancelableCriteriaTestCases {

        private final CancelableFlightFilter underTest =
                new CancelableFlightFilter(FlightSearchCriteria.CancelCriteria.CANCELABLE);

        @Test
        void testFilterWithMultipleFlightsAllCancelableShouldReturnSameFlights() {
            Collection<FlightSummary> flights = List.of(cancelableFlight, cancelableFlight);
            Collection<FlightSummary> result = underTest.filter(flights);
            assertEquals(2, result.size());
            assertThat(result).hasSize(2).containsExactlyElementsOf(flights);
        }

        @Test
        void testFilterWithMultipleFlightsAllNonCancelableShouldReturnEmptyCollection() {
            Collection<FlightSummary> flights = List.of(nonCancelableFlight, nonCancelableFlight);
            Collection<FlightSummary> result = underTest.filter(flights);

            assertThat(result).isEmpty();
        }

        @Test
        void testFilterWithMultipleFlightsMixedCancelableShouldReturnFilteredCollection() {
            Collection<FlightSummary> flights = List.of(nonCancelableFlight, cancelableFlight, nonCancelableFlight);
            Collection<FlightSummary> result = underTest.filter(flights);

            assertThat(result).hasSize(1).containsExactly(cancelableFlight);
        }
    }

    @Nested
    @DisplayName("Test cases for NON_CANCELABLE criteria")
    class NonCancelableCriteriaTestCases {

        private final CancelableFlightFilter underTest =
                new CancelableFlightFilter(FlightSearchCriteria.CancelCriteria.NON_CANCELABLE);

        @Test
        void testFilterWithMultipleFlightsAllNonCancelableShouldReturnSameFlights() {
            Collection<FlightSummary> flights = List.of(nonCancelableFlight, nonCancelableFlight);
            Collection<FlightSummary> result = underTest.filter(flights);
            assertThat(result).hasSize(2).containsExactlyElementsOf(flights);
        }

        @Test
        void testFilterWithMultipleFlightsAllCancelableShouldReturnEmptyCollection() {
            Collection<FlightSummary> flights = List.of(cancelableFlight, cancelableFlight);
            Collection<FlightSummary> result = underTest.filter(flights);
            assertThat(result).isEmpty();
        }

        @Test
        void testFilterWithMultipleFlightsMixedCancelableShouldReturnFilteredCollection() {
            Collection<FlightSummary> flights = List.of(cancelableFlight, nonCancelableFlight);
            Collection<FlightSummary> result = underTest.filter(flights);
            assertThat(result).hasSize(1).containsExactly(nonCancelableFlight);
        }
    }

    @Nested
    @DisplayName("Test cases for BOTH criteria")
    class BothCriteriaTestCases {
        private final CancelableFlightFilter underTest =
                new CancelableFlightFilter(FlightSearchCriteria.CancelCriteria.BOTH);

        @Test
        void testFilterWithMultipleFlightsMixedCancelableShouldReturnAllFlights() {
            Collection<FlightSummary> flights = List.of(cancelableFlight, nonCancelableFlight, cancelableFlight);
            Collection<FlightSummary> result = underTest.filter(flights);
            assertThat(result).hasSize(3).containsExactlyElementsOf(flights);
        }
    }

    @Nested
    @DisplayName("Common test cases")
    class CommonTestCases {
        CancelableFlightFilter underTest = new CancelableFlightFilter(FlightSearchCriteria.CancelCriteria.BOTH);

        @Test
        void testFilterWithNullShouldThrowNullPointerException() {
            assertThatNullPointerException().isThrownBy(() -> underTest.filter(null))
                    .withMessage("flights is marked non-null but is null");
        }

        @Test
        void testFilterWithEmptyCollectionShouldReturnEmptyCollection() {
            Collection<FlightSummary> flights = Collections.emptyList();
            Collection<FlightSummary> result = underTest.filter(flights);
            assertThat(result).isEmpty();
        }
    }
}