package com.happyflights.search.executor.factory.impl;

import com.happyflights.search.executor.FlightSearchExecutor;
import com.happyflights.search.model.FlightSearchCriteria;
import com.happyflights.search.strategy.filter.impl.CancelableFlightFilter;
import com.happyflights.search.strategy.filter.impl.MaximumPriceFlightFilter;
import com.happyflights.search.strategy.filter.impl.NoOpFlightFilter;
import com.happyflights.search.strategy.limit.FlightLimitingStrategy;
import com.happyflights.search.strategy.limit.impl.MaxResultLimiter;
import com.happyflights.search.strategy.sort.impl.LengthFlightSorter;
import com.happyflights.search.strategy.sort.impl.NoOpFlightSorter;
import com.happyflights.search.strategy.sort.impl.PriceFlightSorter;
import com.happyflights.search.strategy.validate.impl.BasicFlightValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class FlightSearchExecutorFactoryImplTest {

    private FlightSearchExecutorFactoryImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new FlightSearchExecutorFactoryImpl();
    }

    @Test
    void testCreateExecutorWithNoCriteriaShouldReturnDefaultStrategies() {
        FlightSearchCriteria criteria = FlightSearchCriteria.builder().build();

        FlightSearchExecutor executor = underTest.createExecutor(criteria);

        assertThat(executor.getFlightValidatingStrategy()).isInstanceOf(BasicFlightValidator.class);
        assertThat(executor.getFlightFilteringStrategies()).hasSize(1);
        assertThat(executor.getFlightFilteringStrategies().getFirst()).isInstanceOf(NoOpFlightFilter.class);
        assertThat(executor.getFlightSortingStrategy()).isInstanceOf(NoOpFlightSorter.class);
        FlightLimitingStrategy flightLimitingStrategy = executor.getFlightLimitingStrategy();
        assertThat(flightLimitingStrategy).isInstanceOf(MaxResultLimiter.class);
        assertThat(((MaxResultLimiter) flightLimitingStrategy).getMaxResult()).isEqualTo(3);
    }

    @Test
    void testCreateExecutorWithCancelableCriteriaShouldReturnCancelableFilter() {
        FlightSearchCriteria criteria = FlightSearchCriteria.builder()
                .cancelable(FlightSearchCriteria.CancelCriteria.BOTH)
                .build();

        FlightSearchExecutor executor = underTest.createExecutor(criteria);

        assertThat(executor.getFlightFilteringStrategies()).hasSize(1);
        assertThat(executor.getFlightFilteringStrategies().getFirst()).isInstanceOf(CancelableFlightFilter.class);
    }

    @Test
    void testCreateExecutorWithMaxPriceCriteriaShouldReturnMaximumPriceFilter() {
        FlightSearchCriteria criteria = FlightSearchCriteria.builder()
                .maxPrice(100.0f)
                .build();

        FlightSearchExecutor executor = underTest.createExecutor(criteria);

        assertThat(executor.getFlightFilteringStrategies()).hasSize(1);
        assertThat(executor.getFlightFilteringStrategies().getFirst()).isInstanceOf(MaximumPriceFlightFilter.class);
    }

    @Test
    void testCreateExecutorWithPriceSortCriteriaShouldReturnPriceSortingStrategy() {
        FlightSearchCriteria criteria = FlightSearchCriteria.builder()
                .sortCriteria(FlightSearchCriteria.SortCriteria.PRICE)
                .build();

        FlightSearchExecutor executor = underTest.createExecutor(criteria);

        assertThat(executor.getFlightSortingStrategy()).isInstanceOf(PriceFlightSorter.class);
    }

    @Test
    void testCreateExecutorWithLengthSortCriteriaShouldReturnLengthSortingStrategy() {
        FlightSearchCriteria criteria = FlightSearchCriteria.builder()
                .sortCriteria(FlightSearchCriteria.SortCriteria.LENGTH)
                .build();

        FlightSearchExecutor executor = underTest.createExecutor(criteria);

        assertThat(executor.getFlightSortingStrategy()).isInstanceOf(LengthFlightSorter.class);
    }

    @Test
    void testCreateExecutorWithMaxResultsCriteriaShouldReturnMaxResultLimitingStrategy() {
        int customMaxResults = 10;
        FlightSearchCriteria criteria = FlightSearchCriteria.builder()
                .maxResults(customMaxResults)
                .build();

        FlightSearchExecutor executor = underTest.createExecutor(criteria);

        FlightLimitingStrategy flightLimitingStrategy = executor.getFlightLimitingStrategy();
        assertThat(flightLimitingStrategy).isInstanceOf(MaxResultLimiter.class);
        assertThat(((MaxResultLimiter) flightLimitingStrategy).getMaxResult()).isEqualTo(customMaxResults);
    }

    @Test
    void testCreateExecutorWithNullCriteriaShouldThrowNullPointerException() {
        assertThatNullPointerException().isThrownBy(() -> underTest.createExecutor(null));
    }
}