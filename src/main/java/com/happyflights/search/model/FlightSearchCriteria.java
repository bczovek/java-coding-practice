package com.happyflights.search.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Value;

import java.util.Date;

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
    private Integer maxResults;

    public enum CancelCriteria {
        CANCELABLE, NON_CANCELABLE, BOTH
    }

    public enum SortCriteria {
        PRICE, LENGTH

    }
}
