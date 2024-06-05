package com.happyflights.search.converter.impl;

import com.happyflights.availability.FlightAvailabilityRequest;
import com.happyflights.search.converter.Converter;
import com.happyflights.search.model.FlightSearchCriteria;
import lombok.NonNull;

public class FlightSearchCriteriaToAvailabilityRequestConverter implements Converter<FlightSearchCriteria, FlightAvailabilityRequest> {
    @Override
    public FlightAvailabilityRequest convert(@NonNull FlightSearchCriteria input) {
        return new FlightAvailabilityRequest(input.getOrigin(), input.getDestination(),
                input.getDepartureDate(), input.getNumberOfTravellers());
    }
}
