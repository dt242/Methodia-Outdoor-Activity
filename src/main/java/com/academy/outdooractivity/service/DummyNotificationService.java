package com.academy.outdooractivity.service;

import com.academy.outdooractivity.model.ActivityResult;
import com.academy.outdooractivity.model.NotificationRule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DummyNotificationService implements NotificationService {

    @Override
    public void notify(List<ActivityResult> results, NotificationRule rule) {
        if (results.isEmpty()) {
            return;
        }

        if (rule.weekendOnly()) {
            boolean hasWeekendActivities = results.stream()
                    .flatMap(activityResult -> activityResult.dayResults().stream())
                    .anyMatch(dayResult -> dayResult.date().getDayOfWeek().getValue() >= 6);

            if (!hasWeekendActivities) {
                System.out.println("Skip email service: found valid intervals but not on a weekend");
                return;
            }
        }

        System.out.println("Dummy email service activated");
        System.out.println("To: " + rule.email());
    }
}