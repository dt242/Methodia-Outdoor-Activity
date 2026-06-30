package com.academy.outdooractivity.util;

import java.time.LocalDate;

public final class DateUtils {

    private DateUtils() {}

    public static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().getValue() >= 6;
    }
}