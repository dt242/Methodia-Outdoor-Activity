package com.academy.outdooractivity.service;

import com.academy.outdooractivity.model.SportRule;
import com.academy.outdooractivity.model.WeatherHour;
import org.springframework.stereotype.Service;

@Service
public class ActivityEvaluator {

    public boolean isSuitable(WeatherHour hour, SportRule rule) {
        if (!hour.isDaylight()) {
            return false;
        }

        if (hour.temperature() < rule.minTemperature() || hour.temperature() > rule.maxTemperature()) {
            return false;
        }

        if (hour.windKph() > rule.maxWindKph()) {
            return false;
        }

        if (hour.chanceOfRain() > rule.maxPrecipitationChance()) {
            return false;
        }
        return true;
    }
}