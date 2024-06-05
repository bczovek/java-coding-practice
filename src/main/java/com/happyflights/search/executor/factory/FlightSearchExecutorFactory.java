package com.happyflights.search.executor.factory;

import com.happyflights.search.executor.FlightSearchExecutor;
import com.happyflights.search.model.FlightSearchCriteria;

/**
 * An interface for creating {@link FlightSearchExecutor} objects based on the provided {@link FlightSearchCriteria}.
 * Implementations of this interface should configure the {@link FlightSearchExecutor} with the appropriate strategies
 * (validation, filtering, sorting, and limiting) based on the search criteria.
 */
public interface FlightSearchExecutorFactory {
    /**
     * Creates a {@link FlightSearchExecutor} object based on the provided {@link FlightSearchCriteria}.
     *
     * @param flightSearchCriteria A {@link FlightSearchCriteria} object containing the search criteria for the flight search.
     * @return A {@link FlightSearchExecutor} object configured with the appropriate strategies based on the provided search criteria.
     */
    FlightSearchExecutor createExecutor(FlightSearchCriteria flightSearchCriteria);
}
