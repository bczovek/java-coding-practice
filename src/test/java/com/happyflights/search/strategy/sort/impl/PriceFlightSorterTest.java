package com.happyflights.search.strategy.sort.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.model.FlightSearchCriteria;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PriceFlightSorterTest {


    @Nested
    @DisplayName("Test cases for ascending sorting")
    class AscendingOrderTests {
        private final PriceFlightSorter underTest = new PriceFlightSorter(FlightSearchCriteria.SortOrder.ASCENDING);

        @Test
        void testSortWithEmptyCollectionShouldReturnEmptyCollection() {
            Collection<FlightSummary> flights = List.of();

            Collection<FlightSummary> result = underTest.sort(flights);

            assertThat(result).isEmpty();
        }

        @Test
        void testSortWithSingleFlightShouldReturnSingleFlight() {
            FlightSummary flight = FlightSummary.builder()
                    .averagePriceInUsd(100.0f)
                    .build();
            Collection<FlightSummary> flights = List.of(flight);

            Collection<FlightSummary> result = underTest.sort(flights);

            assertThat(result).containsExactly(flight);
        }

        @Test
        void testSortWithMultipleFlightsShouldReturnSortedFlights() {
            FlightSummary flight1 = FlightSummary.builder()
                    .averagePriceInUsd(100.0f)
                    .build();
            FlightSummary flight2 = FlightSummary.builder()
                    .averagePriceInUsd(80.0f)
                    .build();
            FlightSummary flight3 = FlightSummary.builder()
                    .averagePriceInUsd(120.0f)
                    .build();
            Collection<FlightSummary> flights = List.of(flight1, flight2, flight3);

            Collection<FlightSummary> result = underTest.sort(flights);

            assertThat(result).containsExactly(flight2, flight1, flight3);
        }

        @Test
        void testSortWithFlightsHavingSamePriceShouldReturnOriginalOrder() {
            FlightSummary flight1 = FlightSummary.builder()
                    .averagePriceInUsd(100.0f)
                    .build();
            FlightSummary flight2 = FlightSummary.builder()
                    .averagePriceInUsd(100.0f)
                    .build();
            Collection<FlightSummary> flights = List.of(flight1, flight2);

            Collection<FlightSummary> result = underTest.sort(flights);

            assertThat(result).containsExactly(flight1, flight2);
        }
    }

    @Nested
    @DisplayName("Test cases for descending sorting")
    class DescendingOrderTests {
        private final PriceFlightSorter underTest = new PriceFlightSorter(FlightSearchCriteria.SortOrder.DESCENDING);

        @Test
        void testSortWithMultipleFlightsShouldReturnSortedFlights() {
            FlightSummary flight1 = FlightSummary.builder()
                    .averagePriceInUsd(100.0f)
                    .build();
            FlightSummary flight2 = FlightSummary.builder()
                    .averagePriceInUsd(80.0f)
                    .build();
            FlightSummary flight3 = FlightSummary.builder()
                    .averagePriceInUsd(120.0f)
                    .build();
            Collection<FlightSummary> flights = List.of(flight1, flight2, flight3);

            Collection<FlightSummary> result = underTest.sort(flights);

            assertThat(result).containsExactly(flight3, flight1, flight2);
        }

        @Test
        void testSortWithFlightsHavingSamePriceShouldReturnOriginalOrder() {
            FlightSummary flight1 = FlightSummary.builder()
                    .averagePriceInUsd(100.0f)
                    .build();
            FlightSummary flight2 = FlightSummary.builder()
                    .averagePriceInUsd(100.0f)
                    .build();
            Collection<FlightSummary> flights = List.of(flight1, flight2);

            Collection<FlightSummary> result = underTest.sort(flights);

            assertThat(result).containsExactly(flight1, flight2);
        }
    }

    @Nested
    @DisplayName("Common test cases")
    class CommonTestCases {
        private final PriceFlightSorter underTest = new PriceFlightSorter();

        @Test
        void testSortWithEmptyCollectionShouldReturnEmptyCollection() {
            Collection<FlightSummary> flights = List.of();

            Collection<FlightSummary> result = underTest.sort(flights);

            assertThat(result).isEmpty();
        }

        @Test
        void testSortWithSingleFlightShouldReturnSingleFlight() {
            FlightSummary flight = FlightSummary.builder()
                    .averagePriceInUsd(100.0f)
                    .build();
            Collection<FlightSummary> flights = List.of(flight);

            Collection<FlightSummary> result = underTest.sort(flights);

            assertThat(result).containsExactly(flight);
        }

        @Test
        void testSortWithNullCollectionShouldThrowNullPointerException() {
            assertThatNullPointerException().isThrownBy(() -> underTest.sort(null));
        }
    }
}