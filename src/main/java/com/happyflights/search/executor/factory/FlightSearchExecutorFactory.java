package com.happyflights.search.executor.factory;

import com.happyflights.search.executor.FlightSearchExecutor;
import com.happyflights.search.executor.FlightSearchExecutorBuilder;
import com.happyflights.search.model.FlightSearchCriteria;
import com.happyflights.search.strategy.filter.impl.CancellableFlightFilter;
import com.happyflights.search.strategy.filter.impl.MaximumPriceFlightFilter;
import com.happyflights.search.strategy.sort.impl.LengthFlightSorting;
import com.happyflights.search.strategy.limit.impl.MaxResultLimiting;
import com.happyflights.search.strategy.sort.impl.PriceFlightSorting;

import java.util.Objects;


public class FlightSearchExecutorFactory {
    public static FlightSearchExecutor createExecutor(FlightSearchCriteria flightSearchCriteria) {
        FlightSearchExecutorBuilder builder = new FlightSearchExecutorBuilder();

        addFilters(flightSearchCriteria, builder);
        addSorter(flightSearchCriteria, builder);
        addLimiter(flightSearchCriteria, builder);

        return builder.build();
    }

    private static void addFilters(FlightSearchCriteria flightSearchCriteria, FlightSearchExecutorBuilder builder) {
        if(!Objects.isNull(flightSearchCriteria.getCancellable())) {
            builder.addFilteringStrategy(new CancellableFlightFilter(flightSearchCriteria.getCancellable()));
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
        builder.withFlightLimitingStrategy(new MaxResultLimiting(3));
    }
}
