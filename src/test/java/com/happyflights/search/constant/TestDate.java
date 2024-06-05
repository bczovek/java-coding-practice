package com.happyflights.search.constant;

public enum TestDate {

    DEPARTURE_DATE("2022-01-01T10:00:00Z"),
    DEPARTURE_DATE_PLUS_TWO_HOURS("2022-01-01T12:00:00Z"),
    DEPARTURE_DATE_PLUS_ONE_HOUR("2022-01-01T11:00:00Z"),
    DEPARTURE_DATE_PLUS_THREE_HOURS("2022-01-01T13:00:00Z"),
    DEPARTURE_DATE_PLUS_FOUR_HOURS("2022-01-01T14:00:00Z");

    private final String value;

    TestDate(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
