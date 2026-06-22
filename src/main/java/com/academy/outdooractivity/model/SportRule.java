package com.academy.outdooractivity.model;

public record SportRule(
        int maxWindKph,
        int maxPrecipitationChance,
        int minTemperature,
        int maxTemperature,
        int minDurationHours,
        boolean preferWeekend,
        int preferCloudCover
) {}