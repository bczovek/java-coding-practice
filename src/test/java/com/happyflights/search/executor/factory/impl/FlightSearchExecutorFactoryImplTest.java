package com.happyflights.search.executor.factory.impl;

import com.happyflights.search.executor.FlightSearchExecutor;
import com.happyflights.search.model.FlightSearchCriteria;
import com.happyflights.search.strategy.filter.impl.CancelableFlightFilter;
import com.happyflights.search.strategy.filter.impl.MaximumPriceFlightFilter;
import com.happyflights.search.strategy.filter.impl.NoOpFlightFilter;
import com.happyflights.search.strategy.limit.FlightLimitingStrategy;
import com.happyflights.search.strategy.limit.impl.MaxResultLimiting;
import com.happyflights.search.strategy.sort.impl.LengthFlightSorting;
import com.happyflights.search.strategy.sort.impl.NoOpFlightSorting;
import com.happyflights.search.strategy.sort.impl.PriceFlightSorting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class FlightSearchExecutorFactoryImplTest {

    private FlightSearchExecutorFactoryImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new FlightSearchExecutorFactoryImpl();
    }

    //TODO validation
    @Test
    void testCreateExecutorWithNoCriteriaShouldReturnNoOpStrategies() {
        FlightSearchCriteria criteria = FlightSearchCriteria.builder().build();

        FlightSearchExecutor executor = underTest.createExecutor(criteria);

        assertThat(executor.getFlightFilteringStrategies()).hasSize(1);
        assertThat(executor.getFlightFilteringStrategies().getFirst()).isInstanceOf(NoOpFlightFilter.class);
        assertThat(executor.getFlightSortStrategy()).isInstanceOf(NoOpFlightSorting.class);
        FlightLimitingStrategy flightLimitingStrategy = executor.getFlightLimitingStrategy();
        assertThat(flightLimitingStrategy).isInstanceOf(MaxResultLimiting.class);
        assertThat(((MaxResultLimiting) flightLimitingStrategy).getMaxResult()).isEqualTo(3);
    }

    @Test
    void testCreateExecutorWithCancelableCriteriaShouldReturnCancelableFilter() {
        FlightSearchCriteria criteria = FlightSearchCriteria.builder()
                .cancellable(FlightSearchCriteria.CancelCriteria.BOTH)
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

        assertThat(executor.getFlightSortStrategy()).isInstanceOf(PriceFlightSorting.class);
    }

    @Test
    void testCreateExecutorWithLengthSortCriteriaShouldReturnLengthSortingStrategy() {
        FlightSearchCriteria criteria = FlightSearchCriteria.builder()
                .sortCriteria(FlightSearchCriteria.SortCriteria.LENGTH)
                .build();

        FlightSearchExecutor executor = underTest.createExecutor(criteria);

        assertThat(executor.getFlightSortStrategy()).isInstanceOf(LengthFlightSorting.class);
    }

    @Test
    void testCreateExecutorWithMaxResultsCriteriaShouldReturnMaxResultLimitingStrategy() {
        int customMaxResults = 10;
        FlightSearchCriteria criteria = FlightSearchCriteria.builder()
                .maxResults(customMaxResults)
                .build();

        FlightSearchExecutor executor = underTest.createExecutor(criteria);

        FlightLimitingStrategy flightLimitingStrategy = executor.getFlightLimitingStrategy();
        assertThat(flightLimitingStrategy).isInstanceOf(MaxResultLimiting.class);
        assertThat(((MaxResultLimiting) flightLimitingStrategy).getMaxResult()).isEqualTo(customMaxResults);
    }

    @Test
    void testCreateExecutorWithNullCriteriaShouldThrowNullPointerException() {
        assertThatNullPointerException().isThrownBy(() -> underTest.createExecutor(null));
    }
}