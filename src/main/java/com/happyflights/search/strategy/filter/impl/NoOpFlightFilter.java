package com.happyflights.search.strategy.filter.impl;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.filter.FlightFilteringStrategy;
import lombok.NonNull;

import java.util.Collection;

public class NoOpFlightFilter implements FlightFilteringStrategy {
    @Override
    public Collection<FlightSummary> filter(@NonNull Collection<FlightSummary> flights) {
        return flights;
    }
}
