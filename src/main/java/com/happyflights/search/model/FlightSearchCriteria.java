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
    private int numberOfTravellers;
    private Boolean cancellable;
    private Float maxPrice;
    private SortCriteria sortCriteria;
    private LimitCriteria limitCriteria;

    public enum SortCriteria {
        PRICE, LENGTH
    }
}
