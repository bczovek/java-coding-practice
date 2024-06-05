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
import lombok.NonNull;

import java.util.Collection;

/**
 An implementation of the {@link FlightSearchService} interface that retrieves flight availability and filters, sorts,
 and limits the results based on the provided {@link FlightSearchCriteria}.
 This class uses the {@link FlightAvailabilityService} to fetch flight availability, {@link FlightSearchExecutorFactory}
 to create a {@link FlightSearchExecutor} with the appropriate strategies, and {@link Converter} to convert the search criteria into
 a flight availability request.
 */
public class FlightSearchServiceImpl implements FlightSearchService {

    private final FlightAvailabilityService availabilityService;
    private final FlightSearchExecutorFactory flightSearchExecutorFactory;
    private final Converter<FlightSearchCriteria, FlightAvailabilityRequest> converter;

    /**
     Constructs a {@link FlightSearchServiceImpl} object with the specified {@link FlightAvailabilityService},
     {@link FlightSearchExecutorFactory}, and {@link Converter}.

     @param availabilityService A {@link FlightAvailabilityService} object to fetch flight availability.
     @param flightSearchExecutorFactory A {@link FlightSearchExecutorFactory} object to
     create a {@link FlightSearchExecutor} with the appropriate strategies.
     @param converter A {@link Converter} object to convert the search criteria into a flight availability request.
     @throws NullPointerException if any of the provided arguments is null. */
    public FlightSearchServiceImpl(@NonNull FlightAvailabilityService availabilityService,
                                   @NonNull FlightSearchExecutorFactory flightSearchExecutorFactory,
                                   @NonNull Converter<FlightSearchCriteria, FlightAvailabilityRequest> converter) {
        this.availabilityService = availabilityService;
        this.flightSearchExecutorFactory = flightSearchExecutorFactory;
        this.converter = converter;
    }

    /**
     Searches for flights based on the provided {@link FlightSearchCriteria}, filters, sorts, and limits the results accordingly.

     @param flightSearchCriteria A {@link FlightSearchCriteria} object containing the search criteria for the flight search.
     @return A collection of {@link FlightSummary} objects that meet the specified search criteria.
     @throws NullPointerException if the flightSearchCriteria is null.
     */
    @Override
    public Collection<FlightSummary> search(@NonNull FlightSearchCriteria flightSearchCriteria) {
        Collection<FlightSummary> flightSummaries =
                availabilityService.getAvailableFlights(converter.convert(flightSearchCriteria));

        FlightSearchExecutor flightSearchExecutor = flightSearchExecutorFactory.createExecutor(flightSearchCriteria);

        return flightSearchExecutor.execute(flightSummaries);
    }
}
