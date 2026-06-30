package com.academy.outdooractivity.service;

import com.academy.outdooractivity.model.ActivityResult;
import com.academy.outdooractivity.model.NotificationRule;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationEvaluator {

    public boolean shouldNotify(List<ActivityResult> results, NotificationRule rule) {
        if (results.isEmpty()) {
            return false;
        }

        if (rule.weekendOnly()) {
            return results.stream()
                    .flatMap(activityResult -> activityResult.dayResults().stream())
                    .anyMatch(dayResult -> dayResult.date().getDayOfWeek().getValue() >= 6);
        }

        return true;
    }
}