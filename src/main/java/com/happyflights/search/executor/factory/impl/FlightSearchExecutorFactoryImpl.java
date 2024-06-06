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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An implementation of the {@link FlightSearchExecutorFactory} interface that creates a {@link FlightSearchExecutor} object based
 * on the provided {@link FlightSearchCriteria}.
 * This implementation uses the {@link FlightSearchExecutorBuilder} to build
 * the {@link FlightSearchExecutor} with the appropriate strategies.
 * By default, if the search criteria do not specify any filtering, sorting, or limiting strategies, the following strategies are used:
 * <ul>
 * <li>Filtering: {@link NoOpFlightFilter}</li>
 * <li>Sorting: {@link NoOpFlightSorter}</li>
 * <li>Limiting: {@link MaxResultLimiter} with the default maximum result count (3)</li>
 * </ul>
 * If the search criteria specify any filtering, sorting, or limiting options, the corresponding strategies are used:
 * <ul>
 * <li>Filtering: {@link CancelableFlightFilter}, {@link MaximumPriceFlightFilter}</li>
 * <li>Sorting: {@link PriceFlightSorter}, {@link LengthFlightSorter}</li>
 * <li>Limiting: {@link MaxResultLimiter} with the specified maximum result count</li>
 * </ul>
 */
public class FlightSearchExecutorFactoryImpl implements FlightSearchExecutorFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightSearchExecutorFactoryImpl.class);

    /**
     * Creates a {@link FlightSearchExecutor} object based on the provided {@link FlightSearchCriteria}.
     *
     * @param flightSearchCriteria A {@link FlightSearchCriteria} object containing the search criteria for the flight search.
     * @return A {@link FlightSearchExecutor} object configured with the appropriate strategies based on the provided search criteria.
     * @throws NullPointerException if the flightSearchCriteria is null.
     */
    @Override
    public FlightSearchExecutor createExecutor(@NonNull FlightSearchCriteria flightSearchCriteria) {
        LOGGER.info("Creating FlightSearchExecutor for criteria: {}", flightSearchCriteria);
        FlightSearchExecutorBuilder builder = new FlightSearchExecutorBuilder()
                .withValidatingStrategy(new BasicFlightValidator());
        LOGGER.debug("Added BasicFlightValidator to FlightSearchExecutorBuilder");

        createFilters(flightSearchCriteria)
                .forEach(builder::addFilteringStrategy);

        FlightSearchExecutor flightSearchExecutor = builder.withFlightSortingStrategy(createSorter(flightSearchCriteria))
                .withFlightLimitingStrategy(createLimiter(flightSearchCriteria))
                .build();
        LOGGER.info("FlightSearchExecutor created for criteria: {}", flightSearchCriteria);
        return flightSearchExecutor;
    }

    /**
     * Creates a list of {@link FlightFilteringStrategy} objects based on the provided {@link FlightSearchCriteria}.
     *
     * @param flightSearchCriteria A {@link FlightSearchCriteria} object containing the search criteria for the flight search.
     * @return A list of {@link FlightFilteringStrategy} objects corresponding to the provided search criteria.
     */
    private List<FlightFilteringStrategy> createFilters(FlightSearchCriteria flightSearchCriteria) {
        List<FlightFilteringStrategy> filters = new ArrayList<>();
        if (!Objects.isNull(flightSearchCriteria.getCancelable())) {
            filters.add(new CancelableFlightFilter(flightSearchCriteria.getCancelable()));
            LOGGER.debug("Added CancelableFlightFilter to FlightSearchExecutorBuilder");
        }
        if (!Objects.isNull(flightSearchCriteria.getMaxPrice())) {
            filters.add(new MaximumPriceFlightFilter(flightSearchCriteria.getMaxPrice()));
            LOGGER.debug("Added MaximumPriceFlightFilter to FlightSearchExecutorBuilder");
        }

        return filters.isEmpty() ? List.of(new NoOpFlightFilter()) : filters;
    }

    /**
     * Creates a {@link FlightSortingStrategy} object based on the provided {@link FlightSearchCriteria}.
     *
     * @param flightSearchCriteria A {@link FlightSearchCriteria} object containing the search criteria for the flight search.
     * @return A {@link FlightSortingStrategy} object corresponding to the provided search criteria.
     */
    private FlightSortingStrategy createSorter(FlightSearchCriteria flightSearchCriteria) {
        switch (flightSearchCriteria.getSortCriteria()) {
            case PRICE -> {
                LOGGER.debug("Added PriceFlightSorter to FlightSearchExecutorBuilder");
                return new PriceFlightSorter(flightSearchCriteria.getSortOrder());
            }
            case LENGTH -> {
                LOGGER.debug("Added LengthFlightSorter to FlightSearchExecutorBuilder");
                return new LengthFlightSorter(flightSearchCriteria.getSortOrder());
            }
            case null, default -> {
                LOGGER.debug("Added NoOpFlightSorter to FlightSearchExecutorBuilder");
                return new NoOpFlightSorter();
            }
        }
    }

    /**
     * Creates a {@link FlightLimitingStrategy} object based on the provided {@link FlightSearchCriteria}.
     *
     * @param flightSearchCriteria A {@link FlightSearchCriteria} object containing the search criteria for the flight search.
     * @return A {@link FlightLimitingStrategy} object corresponding to the provided search criteria.
     */
    private FlightLimitingStrategy createLimiter(FlightSearchCriteria flightSearchCriteria) {
        Integer maxResults = flightSearchCriteria.getMaxResults();
        MaxResultLimiter maxResultLimiter;
        if (!Objects.isNull(maxResults)) {
            maxResultLimiter = new MaxResultLimiter(maxResults);
            LOGGER.debug("Added MaxResultLimiter to FlightSearchExecutorBuilder with limit set to {}.", maxResults);
        } else {
            maxResultLimiter = new MaxResultLimiter();
            LOGGER.debug("Added MaxResultLimiter to FlightSearchExecutorBuilder with default limit (3).");
        }
        return maxResultLimiter;
    }
}
