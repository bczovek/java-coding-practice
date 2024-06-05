package com.happyflights.search.executor.factory.impl;

import com.happyflights.search.executor.FlightSearchExecutor;
import com.happyflights.search.executor.FlightSearchExecutorBuilder;
import com.happyflights.search.executor.factory.FlightSearchExecutorFactory;
import com.happyflights.search.model.FlightSearchCriteria;
import com.happyflights.search.strategy.filter.FlightFilteringStrategy;
import com.happyflights.search.strategy.filter.impl.CancelableFlightFilter;
import com.happyflights.search.strategy.filter.impl.MaximumPriceFlightFilter;
import com.happyflights.search.strategy.filter.impl.NoOpFlightFilter;
import com.happyflights.search.strategy.limit.FlightLimitingStrategy;
import com.happyflights.search.strategy.sort.FlightSortStrategy;
import com.happyflights.search.strategy.sort.impl.LengthFlightSorting;
import com.happyflights.search.strategy.limit.impl.MaxResultLimiting;
import com.happyflights.search.strategy.sort.impl.NoOpFlightSorting;
import com.happyflights.search.strategy.sort.impl.PriceFlightSorting;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FlightSearchExecutorFactoryImpl implements FlightSearchExecutorFactory {

    @Override
    public FlightSearchExecutor createExecutor(@NonNull FlightSearchCriteria flightSearchCriteria) {
        FlightSearchExecutorBuilder builder = new FlightSearchExecutorBuilder();

        createFilters(flightSearchCriteria)
                .forEach(builder::addFilteringStrategy);

        return builder.withFlightSortStrategy(createSorter(flightSearchCriteria))
                .withFlightLimitingStrategy(createLimiter(flightSearchCriteria))
                .build();
    }

    private List<FlightFilteringStrategy> createFilters(FlightSearchCriteria flightSearchCriteria) {
        List<FlightFilteringStrategy> filters = new ArrayList<>();
        if(!Objects.isNull(flightSearchCriteria.getCancellable())) {
            filters.add(new CancelableFlightFilter(flightSearchCriteria.getCancellable()));
        }
        if(!Objects.isNull(flightSearchCriteria.getMaxPrice())) {
            filters.add(new MaximumPriceFlightFilter(flightSearchCriteria.getMaxPrice()));
        }

        return filters.isEmpty() ? List.of(new NoOpFlightFilter()) : filters;
    }

    private FlightSortStrategy createSorter(FlightSearchCriteria flightSearchCriteria) {
        switch (flightSearchCriteria.getSortCriteria()) {
            case PRICE -> {
                return new PriceFlightSorting();
            }
            case LENGTH -> {
                return new LengthFlightSorting();
            }
            case null, default -> {
                return new NoOpFlightSorting();
            }
        }
    }

    private FlightLimitingStrategy createLimiter(FlightSearchCriteria flightSearchCriteria) {
        Integer maxResults = flightSearchCriteria.getMaxResults();
        return !Objects.isNull(maxResults) ? new MaxResultLimiting(maxResults) : new MaxResultLimiting();
    }
}
