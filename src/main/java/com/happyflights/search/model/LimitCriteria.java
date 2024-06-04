package com.happyflights.search.model;

import lombok.Data;

@Data
public class LimitCriteria {

    public static final int DEFAULT_LIMIT = 3;

    private int maxResults = DEFAULT_LIMIT;
    private LimitType limitType;

    public enum LimitType {
        MAX_RESULT
    }
}
