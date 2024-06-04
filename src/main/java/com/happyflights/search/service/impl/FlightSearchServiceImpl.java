package com.happyflights.search.service.impl;

import com.happyflights.availability.FlightAvailabilityRequest;
import com.happyflights.availability.FlightAvailabilityService;
import com.happyflights.availability.FlightSummary;
import com.happyflights.search.executor.FlightSearchExecutor;
import com.happyflights.search.executor.factory.FlightSearchExecutorFactory;
import com.happyflights.search.model.FlightSearchCriteria;
import com.happyflights.search.service.FlightSearchService;

import java.util.Collection;

public class FlightSearchServiceImpl implements FlightSearchService {

    private final FlightAvailabilityService availabilityService;

    public FlightSearchServiceImpl(FlightAvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @Override
    public Collection<FlightSummary> search(FlightSearchCriteria flightSearchCriteria) {
        Collection<FlightSummary> flightSummaries =
                availabilityService.getAvailableFlights(convertToFlightAvailabilityRequest(flightSearchCriteria));
        FlightSearchExecutor flightSearchExecutor = FlightSearchExecutorFactory.createExecutor(flightSearchCriteria);
        return flightSearchExecutor.execute(flightSummaries);
    }

    private FlightAvailabilityRequest convertToFlightAvailabilityRequest(FlightSearchCriteria flightSearchCriteria) {
        return new FlightAvailabilityRequest(flightSearchCriteria.getOrigin(), flightSearchCriteria.getDestination(),
                flightSearchCriteria.getDepartureDate(), flightSearchCriteria.getNumberOfTravellers());
    }

}
