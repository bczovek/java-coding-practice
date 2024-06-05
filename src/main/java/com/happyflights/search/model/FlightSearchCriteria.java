package com.happyflights.search.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class FlightSearchCriteria {

    private String origin;
    private String destination;
    private Date departureDate;
    private Integer numberOfTravellers;
    private CancelCriteria cancellable;
    private Float maxPrice;
    private SortCriteria sortCriteria;
    private Integer maxResults;

    public enum CancelCriteria {
        CANCELABLE, NON_CANCELABLE, BOTH
    }

    public enum SortCriteria {
        PRICE, LENGTH

    }
}
