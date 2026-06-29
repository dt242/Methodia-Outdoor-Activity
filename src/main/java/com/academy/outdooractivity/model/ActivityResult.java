package com.academy.outdooractivity.model;

import java.util.List;

public record ActivityResult(
        String sportName,
        List<DayResult> dayResults
) {
    public boolean hasIntervals() {
        return !dayResults.isEmpty();
    }
}