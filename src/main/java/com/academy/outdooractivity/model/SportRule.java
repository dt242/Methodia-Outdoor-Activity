package com.academy.outdooractivity.model;

public record SportRule(
        int maxGustKph,
        int maxPrecipitationChance,
        int minTemperature,
        int maxTemperature,
        int minDurationHours,
        boolean preferWeekend,
        int preferCloudCover
) {}