package com.happyflights.search.service.impl;

import com.happyflights.availability.FlightAvailabilityRequest;
import com.happyflights.availability.FlightAvailabilityService;
import com.happyflights.availability.FlightSummary;
import com.happyflights.search.converter.Converter;
import com.happyflights.search.converter.impl.FlightSearchCriteriaToAvailabilityRequestConverter;
import com.happyflights.search.executor.FlightSearchExecutor;
import com.happyflights.search.executor.factory.FlightSearchExecutorFactory;
import com.happyflights.search.model.FlightSearchCriteria;
import com.happyflights.search.service.FlightSearchService;

import java.util.Collection;

public class FlightSearchServiceImpl implements FlightSearchService {

    private final FlightAvailabilityService availabilityService;
    private final FlightSearchExecutorFactory flightSearchExecutorFactory;
    private final Converter<FlightSearchCriteria, FlightAvailabilityRequest> converter;

    public FlightSearchServiceImpl(FlightAvailabilityService availabilityService, FlightSearchExecutorFactory flightSearchExecutorFactory, FlightSearchCriteriaToAvailabilityRequestConverter converter) {
        this.availabilityService = availabilityService;
        this.flightSearchExecutorFactory = flightSearchExecutorFactory;
        this.converter = converter;
    }

    @Override
    public Collection<FlightSummary> search(FlightSearchCriteria flightSearchCriteria) {
        Collection<FlightSummary> flightSummaries =
                availabilityService.getAvailableFlights(converter.convert(flightSearchCriteria));

        FlightSearchExecutor flightSearchExecutor = flightSearchExecutorFactory.createExecutor(flightSearchCriteria);

        return flightSearchExecutor.execute(flightSummaries);
    }
}
