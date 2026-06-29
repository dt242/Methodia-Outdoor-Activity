package com.academy.outdooractivity.service;

import com.academy.outdooractivity.model.SportRule;
import com.academy.outdooractivity.model.TimeInterval;
import com.academy.outdooractivity.model.WeatherHour;
import com.academy.outdooractivity.util.ActivityEvaluator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IntervalFinder {

    public IntervalFinder() {}

    public List<TimeInterval> findSuitableIntervals(List<WeatherHour> forecast, SportRule rule) {
        List<TimeInterval> intervals = new ArrayList<>();
        List<WeatherHour> currentBlock = new ArrayList<>();

        for (WeatherHour hour : forecast) {
            if (ActivityEvaluator.isSuitable(hour, rule)) {
                currentBlock.add(hour);
            } else {
                addIntervalIfValid(intervals, currentBlock, rule);
                currentBlock.clear();
            }
        }

        addIntervalIfValid(intervals, currentBlock, rule);
        return intervals;
    }

    private void addIntervalIfValid(List<TimeInterval> intervals, List<WeatherHour> block, SportRule rule) {
        if (!block.isEmpty() && block.size() >= rule.minDurationHours()) {
            int startHour = block.get(0).time().getHour();
            int endHour = block.get(block.size() - 1).time().plusHours(1).getHour();
            double avgCloud = block.stream()
                    .mapToInt(WeatherHour::cloudCover)
                    .average()
                    .orElse(0);
            boolean isCloudPreferred = rule.preferCloudCover() > 0 && avgCloud >= rule.preferCloudCover();
            intervals.add(new TimeInterval(startHour, endHour, isCloudPreferred));
        }
    }
}