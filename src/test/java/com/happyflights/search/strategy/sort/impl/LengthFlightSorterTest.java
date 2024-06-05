package com.happyflights.search.strategy.sort.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.model.FlightSearchCriteria;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.happyflights.search.constant.TestDate.DEPARTURE_DATE;
import static com.happyflights.search.constant.TestDate.DEPARTURE_DATE_PLUS_FOUR_HOURS;
import static com.happyflights.search.constant.TestDate.DEPARTURE_DATE_PLUS_ONE_HOUR;
import static com.happyflights.search.constant.TestDate.DEPARTURE_DATE_PLUS_THREE_HOURS;
import static com.happyflights.search.constant.TestDate.DEPARTURE_DATE_PLUS_TWO_HOURS;
import static org.assertj.core.api.Assertions.*;

class LengthFlightSorterTest {

    @Nested
    @DisplayName("Test cases for ascending sorting")
    class AscendingOrderTests {

        private final LengthFlightSorter underTest = new LengthFlightSorter(FlightSearchCriteria.SortOrder.ASCENDING);

        @Test
        void testSortWithMultipleFlightsShouldReturnSortedFlights() {
            FlightSummary flight1 = FlightSummary.builder()
                    .departureTime(Date.from(Instant.parse(DEPARTURE_DATE.getValue())))
                    .arrivalTime(Date.from(Instant.parse(DEPARTURE_DATE_PLUS_TWO_HOURS.getValue())))
                    .build();
            FlightSummary flight2 = FlightSummary.builder()
                    .departureTime(Date.from(Instant.parse(DEPARTURE_DATE.getValue())))
                    .arrivalTime(Date.from(Instant.parse(DEPARTURE_DATE_PLUS_ONE_HOUR.getValue())))
                    .build();
            FlightSummary flight3 = FlightSummary.builder()
                    .departureTime(Date.from(Instant.parse(DEPARTURE_DATE.getValue())))
                    .arrivalTime(Date.from(Instant.parse(DEPARTURE_DATE_PLUS_THREE_HOURS.getValue())))
                    .build();
            Collection<FlightSummary> flights = List.of(flight1, flight2, flight3);

            Collection<FlightSummary> result = underTest.sort(flights);

            assertThat(result).containsExactly(flight2, flight1, flight3);
        }

        @Test
        void testSortWithFlightsHavingSameLengthShouldReturnOriginalOrder() {
            FlightSummary flight1 = FlightSummary.builder()
                    .departureTime(Date.from(Instant.parse(DEPARTURE_DATE.getValue())))
                    .arrivalTime(Date.from(Instant.parse(DEPARTURE_DATE_PLUS_TWO_HOURS.getValue())))
                    .build();
            FlightSummary flight2 = FlightSummary.builder()
                    .departureTime(Date.from(Instant.parse(DEPARTURE_DATE_PLUS_TWO_HOURS.getValue())))
                    .arrivalTime(Date.from(Instant.parse(DEPARTURE_DATE_PLUS_FOUR_HOURS.getValue())))
                    .build();
            Collection<FlightSummary> flights = List.of(flight1, flight2);

            Collection<FlightSummary> result = underTest.sort(flights);

            assertThat(result).containsExactly(flight1, flight2);
        }
    }

    @Nested
    @DisplayName("Test cases for descending sorting")
    class DescendingOrderTests {

        private final LengthFlightSorter underTest = new LengthFlightSorter(FlightSearchCriteria.SortOrder.DESCENDING);

        @Test
        void testSortWithMultipleFlightsShouldReturnSortedFlights() {
            FlightSummary flight1 = FlightSummary.builder()
                    .departureTime(Date.from(Instant.parse(DEPARTURE_DATE.getValue())))
                    .arrivalTime(Date.from(Instant.parse(DEPARTURE_DATE_PLUS_TWO_HOURS.getValue())))
                    .build();
            FlightSummary flight2 = FlightSummary.builder()
                    .departureTime(Date.from(Instant.parse(DEPARTURE_DATE.getValue())))
                    .arrivalTime(Date.from(Instant.parse(DEPARTURE_DATE_PLUS_ONE_HOUR.getValue())))
                    .build();
            FlightSummary flight3 = FlightSummary.builder()
                    .departureTime(Date.from(Instant.parse(DEPARTURE_DATE.getValue())))
                    .arrivalTime(Date.from(Instant.parse(DEPARTURE_DATE_PLUS_THREE_HOURS.getValue())))
                    .build();
            Collection<FlightSummary> flights = List.of(flight1, flight2, flight3);

            Collection<FlightSummary> result = underTest.sort(flights);

            assertThat(result).containsExactly(flight3, flight1, flight2);
        }

        @Test
        void testSortWithFlightsHavingSameLengthShouldReturnOriginalOrder() {
            FlightSummary flight1 = FlightSummary.builder()
                    .departureTime(Date.from(Instant.parse(DEPARTURE_DATE.getValue())))
                    .arrivalTime(Date.from(Instant.parse(DEPARTURE_DATE_PLUS_TWO_HOURS.getValue())))
                    .build();
            FlightSummary flight2 = FlightSummary.builder()
                    .departureTime(Date.from(Instant.parse(DEPARTURE_DATE_PLUS_TWO_HOURS.getValue())))
                    .arrivalTime(Date.from(Instant.parse(DEPARTURE_DATE_PLUS_FOUR_HOURS.getValue())))
                    .build();
            Collection<FlightSummary> flights = List.of(flight1, flight2);

            Collection<FlightSummary> result = underTest.sort(flights);

            assertThat(result).containsExactly(flight1, flight2);
        }
    }

    @Nested
    @DisplayName("Common test cases")
    class CommonTestCases {
        private final LengthFlightSorter underTest = new LengthFlightSorter();

        @Test
        void testSortWithEmptyCollectionShouldReturnEmptyCollection() {
            Collection<FlightSummary> flights = List.of();

            Collection<FlightSummary> result = underTest.sort(flights);

            assertThat(result).isEmpty();
        }

        @Test
        void testSortWithSingleFlightShouldReturnSingleFlight() {
            FlightSummary flight = FlightSummary.builder()
                    .departureTime(Date.from(Instant.parse(DEPARTURE_DATE.getValue())))
                    .arrivalTime(Date.from(Instant.parse(DEPARTURE_DATE_PLUS_TWO_HOURS.getValue())))
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