package com.happyflights.search.strategy.limit;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.model.LimitCriteria;

import java.util.Collection;
import java.util.List;

public interface FlightLimitingStrategy {

    Collection<FlightSummary> limit(Collection<FlightSummary> flights);

}
