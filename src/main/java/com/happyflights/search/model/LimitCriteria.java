package com.happyflights.search.model;

import lombok.Data;

@Data
public class LimitCriteria {
    //TODO: Find better solution, preferably something similar to SortCriteria
    public static final int DEFAULT_LIMIT = 3;

    private int maxResults = DEFAULT_LIMIT;
}
