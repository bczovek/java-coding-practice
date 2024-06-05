package com.happyflights.search.executor.factory.impl;

import com.happyflights.search.executor.FlightSearchExecutor;
import com.happyflights.search.executor.FlightSearchExecutorBuilder;
import com.happyflights.search.executor.factory.FlightSearchExecutorFactory;
import com.happyflights.search.model.FlightSearchCriteria;
import com.happyflights.search.strategy.filter.impl.CancelableFlightFilter;
import com.happyflights.search.strategy.filter.impl.MaximumPriceFlightFilter;
import com.happyflights.search.strategy.sort.impl.LengthFlightSorting;
import com.happyflights.search.strategy.limit.impl.MaxResultLimiting;
import com.happyflights.search.strategy.sort.impl.PriceFlightSorting;

import java.util.Objects;


public class FlightSearchExecutorFactoryImpl implements FlightSearchExecutorFactory {

    @Override
    public FlightSearchExecutor createExecutor(FlightSearchCriteria flightSearchCriteria) {
        FlightSearchExecutorBuilder builder = new FlightSearchExecutorBuilder();

        addFilters(flightSearchCriteria, builder);
        addSorter(flightSearchCriteria, builder);
        addLimiter(flightSearchCriteria, builder);

        return builder.build();
    }

    private static void addFilters(FlightSearchCriteria flightSearchCriteria, FlightSearchExecutorBuilder builder) {
        if(!Objects.isNull(flightSearchCriteria.getCancellable())) {
            builder.addFilteringStrategy(new CancelableFlightFilter(flightSearchCriteria.getCancellable()));
        }
        if(!Objects.isNull(flightSearchCriteria.getMaxPrice())) {
            builder.addFilteringStrategy(new MaximumPriceFlightFilter(flightSearchCriteria.getMaxPrice()));
        }
    }

    private static void addSorter(FlightSearchCriteria flightSearchCriteria, FlightSearchExecutorBuilder builder) {
        switch (flightSearchCriteria.getSortCriteria()) {
            case PRICE -> builder.withFlightSortStrategy(new PriceFlightSorting());
            case LENGTH -> builder.withFlightSortStrategy(new LengthFlightSorting());
        }
    }

    private static void addLimiter(FlightSearchCriteria flightSearchCriteria, FlightSearchExecutorBuilder builder) {
        Integer maxResults = flightSearchCriteria.getMaxResults();
        builder.withFlightLimitingStrategy(!Objects.isNull(maxResults) ?
                new MaxResultLimiting(maxResults) : new MaxResultLimiting());
    }
}
