package com.happyflights.search.service.impl;

import com.happyflights.availability.FlightAvailabilityRequest;
import com.happyflights.availability.FlightAvailabilityService;
import com.happyflights.availability.FlightSummary;
import com.happyflights.search.converter.impl.FlightSearchCriteriaToAvailabilityRequestConverter;
import com.happyflights.search.executor.FlightSearchExecutor;
import com.happyflights.search.executor.factory.FlightSearchExecutorFactory;
import com.happyflights.search.model.FlightSearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.happyflights.search.constant.TestDate.DEPARTURE_DATE;
import static com.happyflights.search.constant.TestDate.DEPARTURE_DATE_PLUS_TWO_HOURS;
import static org.assertj.core.api.Assertions.*;

class FlightSearchServiceImplTest {

    private FlightSearchServiceImpl underTest;
    private FlightAvailabilityService mockAvailabilityService;
    private FlightSearchExecutorFactory mockExecutorFactory;
    private FlightSearchCriteriaToAvailabilityRequestConverter mockConverter;

    @BeforeEach
    public void setUp() {
        mockAvailabilityService = Mockito.mock(FlightAvailabilityService.class);
        mockExecutorFactory = Mockito.mock(FlightSearchExecutorFactory.class);
        mockConverter = Mockito.mock(FlightSearchCriteriaToAvailabilityRequestConverter.class);

        underTest = new FlightSearchServiceImpl(mockAvailabilityService, mockExecutorFactory, mockConverter);
    }

    @Test
    public void testSearchShouldReturnFilteredSortedAndLimitedFlights() {
        FlightSearchCriteria criteria = FlightSearchCriteria.builder()
                .origin("NYC")
                .destination("LAX")
                .departureDate(Date.from(Instant.parse(DEPARTURE_DATE.getValue())))
                .numberOfTravellers(2)
                .build();

        FlightAvailabilityRequest availabilityRequest = new FlightAvailabilityRequest("NYC", "LAX",
                Date.from(Instant.parse(DEPARTURE_DATE.getValue())), 2);

        FlightSummary flight = FlightSummary.builder()
                .departureTime(Date.from(Instant.parse(DEPARTURE_DATE.getValue())))
                .arrivalTime(Date.from(Instant.parse(DEPARTURE_DATE_PLUS_TWO_HOURS.getValue())))
                .averagePriceInUsd(100.0f)
                .build();

        Collection<FlightSummary> flightSummaries = List.of(flight);

        FlightSearchExecutor mockExecutor = Mockito.mock(FlightSearchExecutor.class);

        Mockito.when(mockConverter.convert(criteria)).thenReturn(availabilityRequest);
        Mockito.when(mockAvailabilityService.getAvailableFlights(availabilityRequest)).thenReturn(flightSummaries);
        Mockito.when(mockExecutorFactory.createExecutor(criteria)).thenReturn(mockExecutor);
        Mockito.when(mockExecutor.execute(flightSummaries)).thenReturn(flightSummaries);

        Collection<FlightSummary> result = underTest.search(criteria);

        assertThat(result).containsExactly(flight);
        Mockito.verify(mockConverter).convert(criteria);
        Mockito.verify(mockAvailabilityService).getAvailableFlights(availabilityRequest);
        Mockito.verify(mockExecutorFactory).createExecutor(criteria);
        Mockito.verify(mockExecutor).execute(flightSummaries);
        Mockito.verifyNoMoreInteractions(mockConverter, mockAvailabilityService, mockExecutorFactory, mockExecutor);
    }

    @Test
    public void testSearchWithNullCriteriaShouldThrowNullPointerException() {
        assertThatNullPointerException().isThrownBy(() -> underTest.search(null));
    }

}