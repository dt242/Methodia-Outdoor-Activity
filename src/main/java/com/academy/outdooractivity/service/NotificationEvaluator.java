package com.academy.outdooractivity.service;

import com.academy.outdooractivity.model.ActivityResult;
import com.academy.outdooractivity.model.DayResult;
import com.academy.outdooractivity.model.NotificationRule;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationEvaluator {

    public List<ActivityResult> filterForNotification(List<ActivityResult> results, NotificationRule rule) {
        if (results.isEmpty()) {
            return results;
        }

        if (!rule.weekendOnly()) {
            return results;
        }

        return results.stream()
                .map(result -> {
                    List<DayResult> weekendDays = result.dayResults().stream()
                            .filter(day -> day.date().getDayOfWeek().getValue() >= 6)
                            .toList();

                    return new ActivityResult(result.sportName(), weekendDays);
                })
                .filter(ActivityResult::hasIntervals)
                .toList();
    }
}