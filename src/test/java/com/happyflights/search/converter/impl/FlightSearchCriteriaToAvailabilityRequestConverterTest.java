package com.happyflights.search.converter.impl;

import com.happyflights.availability.FlightAvailabilityRequest;
import com.happyflights.search.model.FlightSearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FlightSearchCriteriaToAvailabilityRequestConverterTest {

    private FlightSearchCriteriaToAvailabilityRequestConverter underTest;

    @BeforeEach
    void setUp() {
        underTest = new FlightSearchCriteriaToAvailabilityRequestConverter();
    }

    @Test
    void testConvertShouldReturnFlightAvailabilityRequest() {
        FlightSearchCriteria criteria = FlightSearchCriteria.builder()
                .origin("NYC")
                .destination("LAX")
                .departureDate(Date.from(Instant.parse("2022-01-01T10:00:00Z")))
                .numberOfTravellers(2)
                .build();

        FlightAvailabilityRequest result = underTest.convert(criteria);

        assertThat(result.getOrigin()).isEqualTo("NYC");
        assertThat(result.getDestination()).isEqualTo("LAX");
        assertThat(result.getDepartureDate()).isEqualTo(Date.from(Instant.parse("2022-01-01T10:00:00Z")));
        assertThat(result.getNumberOfTravellers()).isEqualTo(2);
    }

    @Test
    void testConvertWithNullCriteriaValueShouldThrowNullPointerException() {
        FlightSearchCriteria criteria = FlightSearchCriteria.builder()
                .origin(null)
                .destination("LAX")
                .departureDate(Date.from(Instant.parse("2022-01-01T10:00:00Z")))
                .numberOfTravellers(2)
                .build();

        assertThatThrownBy(() -> underTest.convert(criteria))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void testConvertWithNullCriteriaShouldThrowNullPointerException() {
        assertThatThrownBy(() -> underTest.convert(null))
                .isInstanceOf(NullPointerException.class);
    }

}