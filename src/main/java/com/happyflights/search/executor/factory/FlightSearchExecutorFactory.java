package com.happyflights.search.executor.factory;

import com.happyflights.search.executor.FlightSearchExecutor;
import com.happyflights.search.model.FlightSearchCriteria;

public interface FlightSearchExecutorFactory {

    FlightSearchExecutor createExecutor(FlightSearchCriteria flightSearchCriteria);

}
