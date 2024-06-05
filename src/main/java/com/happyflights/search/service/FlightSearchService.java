package com.happyflights.search.service;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.model.FlightSearchCriteria;

import java.util.Collection;

/**
 * An interface for a flight search service that retrieves flight availability and filters, sorts,
 * and limits the results based on the provided {@link FlightSearchCriteria}.
 */
public interface FlightSearchService {
    Collection<FlightSummary> search(FlightSearchCriteria flightSearchCriteria);
}
