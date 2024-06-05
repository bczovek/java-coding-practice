package com.happyflights.search.strategy.validate.impl;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.validate.exception.NegativeFlightDurationException;
import com.happyflights.search.strategy.validate.exception.NegativeFlightPriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BasicFlightValidationTest {

    private BasicFlightValidation underTest;

    @BeforeEach
    void setUp() {
        underTest = new BasicFlightValidation();
    }

    @Test
    void testValidateWithNullDepartureTimeInFlightShouldThrowIllegalArgumentException() {
        FlightSummary flight = FlightSummary.builder()
                .departureTime(null)
                .arrivalTime(Date.from(Instant.parse("2022-01-01T12:00:00Z")))
                .build();
        Collection<FlightSummary> flights = List.of(flight);

        assertThatIllegalArgumentException().isThrownBy(() -> underTest.validate(flights));
    }

    @Test
    void testValidateWithNullArrivalTimeInFlightShouldThrowIllegalArgumentException() {
        FlightSummary flight = FlightSummary.builder()
                .departureTime(Date.from(Instant.parse("2022-01-01T10:00:00Z")))
                .arrivalTime(null)
                .build();
        Collection<FlightSummary> flights = List.of(flight);

        assertThatIllegalArgumentException().isThrownBy(() -> underTest.validate(flights));
    }

    @Test
    void testValidateWithNegativeFlightLengthShouldThrowNegativeFlightDurationException() {
        FlightSummary flight = FlightSummary.builder()
                .departureTime(Date.from(Instant.parse("2022-01-01T12:00:00Z")))
                .arrivalTime(Date.from(Instant.parse("2022-01-01T10:00:00Z")))
                .build();
        Collection<FlightSummary> flights = List.of(flight);

        assertThatThrownBy(() -> underTest.validate(flights))
                .isInstanceOf(NegativeFlightDurationException.class)
                .hasMessage(String.format("Flight has negative duration. Flight: %s", flight));
    }

    @Test
    void testValidateWithNegativePriceInFlightShouldThrowNegativeFlightPriceException() {
        FlightSummary flight = FlightSummary.builder()
                .averagePriceInUsd(-1f)
                .build();
        Collection<FlightSummary> flights = List.of(flight);

        assertThatThrownBy(() -> underTest.validate(flights))
                .isInstanceOf(NegativeFlightPriceException.class);
    }

    @Test
    public void testValidateWithValidFlightsShouldNotThrowException() {
        FlightSummary flight1 = FlightSummary.builder()
                .departureTime(Date.from(Instant.parse("2022-01-01T10:00:00Z")))
                .arrivalTime(Date.from(Instant.parse("2022-01-01T12:00:00Z")))
                .averagePriceInUsd(100.0f)
                .build();

        FlightSummary flight2 = FlightSummary.builder()
                .departureTime(Date.from(Instant.parse("2022-01-01T14:00:00Z")))
                .arrivalTime(Date.from(Instant.parse("2022-01-01T16:00:00Z")))
                .averagePriceInUsd(150.0f)
                .build();

        Collection<FlightSummary> flights = List.of(flight1, flight2);

        assertThatCode(() -> underTest.validate(flights)).doesNotThrowAnyException();
    }
}