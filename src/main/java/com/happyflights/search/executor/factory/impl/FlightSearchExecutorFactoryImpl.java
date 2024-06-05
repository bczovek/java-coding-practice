package com.happyflights.search.executor.factory.impl;

import com.happyflights.search.executor.FlightSearchExecutor;
import com.happyflights.search.executor.builder.FlightSearchExecutorBuilder;
import com.happyflights.search.executor.factory.FlightSearchExecutorFactory;
import com.happyflights.search.model.FlightSearchCriteria;
import com.happyflights.search.strategy.filter.FlightFilteringStrategy;
import com.happyflights.search.strategy.filter.impl.CancelableFlightFilter;
import com.happyflights.search.strategy.filter.impl.MaximumPriceFlightFilter;
import com.happyflights.search.strategy.filter.impl.NoOpFlightFilter;
import com.happyflights.search.strategy.limit.FlightLimitingStrategy;
import com.happyflights.search.strategy.limit.impl.MaxResultLimiter;
import com.happyflights.search.strategy.sort.FlightSortingStrategy;
import com.happyflights.search.strategy.sort.impl.LengthFlightSorter;
import com.happyflights.search.strategy.sort.impl.NoOpFlightSorter;
import com.happyflights.search.strategy.sort.impl.PriceFlightSorter;
import com.happyflights.search.strategy.validate.impl.BasicFlightValidator;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FlightSearchExecutorFactoryImpl implements FlightSearchExecutorFactory {

    @Override
    public FlightSearchExecutor createExecutor(@NonNull FlightSearchCriteria flightSearchCriteria) {
        FlightSearchExecutorBuilder builder = new FlightSearchExecutorBuilder();

        builder.withValidatingStrategy(new BasicFlightValidator());

        createFilters(flightSearchCriteria)
                .forEach(builder::addFilteringStrategy);

        return builder.withFlightSortingStrategy(createSorter(flightSearchCriteria))
                .withFlightLimitingStrategy(createLimiter(flightSearchCriteria))
                .build();
    }

    private List<FlightFilteringStrategy> createFilters(FlightSearchCriteria flightSearchCriteria) {
        List<FlightFilteringStrategy> filters = new ArrayList<>();
        if (!Objects.isNull(flightSearchCriteria.getCancelable())) {
            filters.add(new CancelableFlightFilter(flightSearchCriteria.getCancelable()));
        }
        if (!Objects.isNull(flightSearchCriteria.getMaxPrice())) {
            filters.add(new MaximumPriceFlightFilter(flightSearchCriteria.getMaxPrice()));
        }

        return filters.isEmpty() ? List.of(new NoOpFlightFilter()) : filters;
    }

    private FlightSortingStrategy createSorter(FlightSearchCriteria flightSearchCriteria) {
        switch (flightSearchCriteria.getSortCriteria()) {
            case PRICE -> {
                return new PriceFlightSorter(flightSearchCriteria.getSortOrder());
            }
            case LENGTH -> {
                return new LengthFlightSorter(flightSearchCriteria.getSortOrder());
            }
            case null, default -> {
                return new NoOpFlightSorter();
            }
        }
    }

    private FlightLimitingStrategy createLimiter(FlightSearchCriteria flightSearchCriteria) {
        Integer maxResults = flightSearchCriteria.getMaxResults();
        return !Objects.isNull(maxResults) ? new MaxResultLimiter(maxResults) : new MaxResultLimiter();
    }
}
