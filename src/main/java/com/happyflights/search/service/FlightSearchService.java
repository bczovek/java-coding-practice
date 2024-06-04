package com.happyflights.search.service;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.model.FlightSearchCriteria;

import java.util.Collection;

public interface FlightSearchService {

    Collection<FlightSummary> search(FlightSearchCriteria flightSearchCriteria);

}
