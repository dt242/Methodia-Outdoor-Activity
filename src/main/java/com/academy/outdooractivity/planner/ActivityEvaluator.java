package com.academy.outdooractivity.planner;

import com.academy.outdooractivity.model.SportRule;
import com.academy.outdooractivity.model.WeatherHour;

public final class ActivityEvaluator {

    private ActivityEvaluator() {}

    public static boolean isSuitable(WeatherHour hour, SportRule rule) {
        if (!hour.isDaylight()) {
            return false;
        }

        if (hour.temperature() < rule.minTemperature() || hour.temperature() > rule.maxTemperature()) {
            return false;
        }

        if (hour.gustKph() > rule.maxGustKph()) {
            return false;
        }

        if (hour.chanceOfRain() > rule.maxPrecipitationChance()) {
            return false;
        }
        return true;
    }
}