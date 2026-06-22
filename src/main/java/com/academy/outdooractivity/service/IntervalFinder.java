package com.academy.outdooractivity.service;

import com.academy.outdooractivity.model.SportRule;
import com.academy.outdooractivity.model.WeatherHour;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IntervalFinder {

    private final ActivityEvaluator evaluator;

    public IntervalFinder(ActivityEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    public List<String> findSuitableIntervals(List<WeatherHour> forecast, SportRule rule) {
        List<String> intervals = new ArrayList<>();

        WeatherHour intervalStart = null;
        WeatherHour intervalEnd = null;

        for (WeatherHour hour : forecast) {
            boolean isGood = evaluator.isSuitable(hour, rule);

            if (isGood) {
                if (intervalStart == null) {
                    intervalStart = hour;
                }
                intervalEnd = hour;
            } else {
                if (intervalStart != null) {
                    if (getDurationHours(intervalStart, intervalEnd) >= rule.minDurationHours()) {
                        intervals.add(formatInterval(intervalStart, intervalEnd));
                    }
                    intervalStart = null;
                    intervalEnd = null;
                }
            }
        }

        if (intervalStart != null) {
            if (getDurationHours(intervalStart, intervalEnd) >= rule.minDurationHours()) {
                intervals.add(formatInterval(intervalStart, intervalEnd));
            }
        }

        return intervals;
    }

    private long getDurationHours(WeatherHour start, WeatherHour end) {
        return end.time().getHour() - start.time().getHour() + 1;
    }

    private String formatInterval(WeatherHour start, WeatherHour end) {
        int startHour = start.time().getHour();
        int endHour = end.time().plusHours(1).getHour();

        return String.format("%02d-%02d", startHour, endHour);
    }
}