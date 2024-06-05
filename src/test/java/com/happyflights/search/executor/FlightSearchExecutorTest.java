package com.happyflights.search.executor;

import com.happyflights.availability.FlightSummary;
import com.happyflights.search.strategy.filter.FlightFilteringStrategy;
import com.happyflights.search.strategy.limit.FlightLimitingStrategy;
import com.happyflights.search.strategy.sort.FlightSortStrategy;
import com.happyflights.search.strategy.validate.FlightValidatingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class FlightSearchExecutorTest {

    private FlightSearchExecutor underTest;
    @Mock
    private FlightValidatingStrategy mockValidatingStrategy;
    @Mock
    private FlightFilteringStrategy mockFilteringStrategy1;
    @Mock
    private FlightFilteringStrategy mockFilteringStrategy2;
    @Mock
    private FlightSortStrategy mockSortStrategy;
    @Mock
    private FlightLimitingStrategy mockLimitingStrategy;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        List<FlightFilteringStrategy> filteringStrategies = new ArrayList<>();
        filteringStrategies.add(mockFilteringStrategy1);
        filteringStrategies.add(mockFilteringStrategy2);

        underTest = new FlightSearchExecutor(mockValidatingStrategy, filteringStrategies, mockSortStrategy, mockLimitingStrategy);
    }

    @Test
    public void testExecuteShouldCallFilterSortAndLimitStrategies() {
        FlightSummary flight = FlightSummary.builder().build();
        Collection<FlightSummary> flights = List.of(flight);

        Mockito.when(mockFilteringStrategy1.filter(flights)).thenReturn(flights);
        Mockito.when(mockFilteringStrategy2.filter(flights)).thenReturn(flights);
        Mockito.when(mockSortStrategy.sort(flights)).thenReturn(flights);
        Mockito.when(mockLimitingStrategy.limit(flights)).thenReturn(flights);

        Collection<FlightSummary> result = underTest.execute(flights);

        assertThat(result).containsExactly(flight);
        Mockito.verify(mockValidatingStrategy).validate(flights);
        Mockito.verify(mockFilteringStrategy1).filter(flights);
        Mockito.verify(mockFilteringStrategy2).filter(flights);
        Mockito.verify(mockSortStrategy).sort(flights);
        Mockito.verify(mockLimitingStrategy).limit(flights);
        Mockito.verifyNoMoreInteractions(mockValidatingStrategy, mockFilteringStrategy1, mockFilteringStrategy2,
                mockSortStrategy, mockLimitingStrategy);
    }

    @Test
    public void testExecuteWithNullFlightSummariesShouldThrowNullPointerException() {
        assertThatNullPointerException().isThrownBy(() -> underTest.execute(null));
    }

}