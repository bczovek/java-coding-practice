package com.happyflights.search.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

/**
 A builder-pattern-based class representing the search criteria for a flight search.
 This class contains various search criteria such as origin, destination, departure date, number of travellers, cancellability,
 maximum price, sorting criteria, sorting order, and maximum results.
 */
@Builder
@Data
public class FlightSearchCriteria {

    @NonNull
    private String origin;
    @NonNull
    private String destination;
    @NonNull
    private Date departureDate;
    @NonNull
    private Integer numberOfTravellers;
    private CancelCriteria cancelable;
    private Float maxPrice;
    private SortCriteria sortCriteria;
    private SortOrder sortOrder;
    private Integer maxResults;

    /**
     An enumeration representing the cancelability criteria for a flight search.
     */
    public enum CancelCriteria {
        CANCELABLE, NON_CANCELABLE, BOTH
    }

    /**
     An enumeration representing the sorting criteria for a flight search.
     */
    public enum SortCriteria {
        PRICE, LENGTH

    }

    /**
     An enumeration representing the sorting order for a flight search.
     */
    public enum SortOrder {
        ASCENDING, DESCENDING
    }
}
