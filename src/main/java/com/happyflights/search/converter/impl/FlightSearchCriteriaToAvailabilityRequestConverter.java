package com.happyflights.search.converter.impl;

import com.happyflights.availability.FlightAvailabilityRequest;
import com.happyflights.search.converter.Converter;
import com.happyflights.search.model.FlightSearchCriteria;
import lombok.NonNull;

/**
 A class that implements the {@link Converter} interface to convert a {@link FlightSearchCriteria} object to
 a {@link FlightAvailabilityRequest} object.
 This converter is useful for transforming a flight search criteria object, which contains various search criteria,
 into a flight availability request object, which is used to query flight availability.
 */
public class FlightSearchCriteriaToAvailabilityRequestConverter implements Converter<FlightSearchCriteria, FlightAvailabilityRequest> {

    /**
     Converts a {@link FlightSearchCriteria} object to a {@link FlightAvailabilityRequest} object.

     @param input A non-null {@link FlightSearchCriteria} object containing the search criteria for the flight search.
     @return A {@link FlightAvailabilityRequest} object containing the origin, destination, departure date,
     and number of travellers from the input {@link FlightSearchCriteria}.
     @throws NullPointerException if the input is null.
     */
    @Override
    public FlightAvailabilityRequest convert(@NonNull FlightSearchCriteria input) {
        return new FlightAvailabilityRequest(input.getOrigin(), input.getDestination(),
                input.getDepartureDate(), input.getNumberOfTravellers());
    }
}
