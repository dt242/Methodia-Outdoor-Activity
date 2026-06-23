package com.academy.outdooractivity.model;

import java.time.LocalDate;
import java.util.List;

public record DayResult(
        LocalDate date,
        List<TimeInterval> intervals,
        boolean preferredWeekend
) {}