package com.happyflights.search.converter.impl;

import com.happyflights.availability.FlightAvailabilityRequest;
import com.happyflights.search.model.FlightSearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static com.happyflights.search.constant.TestDate.DEPARTURE_DATE;
import static org.assertj.core.api.Assertions.*;

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
                .departureDate(Date.from(Instant.parse(DEPARTURE_DATE.getValue())))
                .numberOfTravellers(2)
                .build();

        FlightAvailabilityRequest result = underTest.convert(criteria);

        assertThat(result.getOrigin()).isEqualTo("NYC");
        assertThat(result.getDestination()).isEqualTo("LAX");
        assertThat(result.getDepartureDate()).isEqualTo(Date.from(Instant.parse(DEPARTURE_DATE.getValue())));
        assertThat(result.getNumberOfTravellers()).isEqualTo(2);
    }
}