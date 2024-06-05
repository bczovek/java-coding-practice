package com.happyflights.search.strategy.validate;

import java.util.Collection;

import com.happyflights.availability.FlightSummary;
import lombok.NonNull;

public interface FlightValidationStrategy {

    void validate(@NonNull Collection<FlightSummary> flights);
}
