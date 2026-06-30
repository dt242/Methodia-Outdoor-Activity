package com.academy.outdooractivity.service;

import com.academy.outdooractivity.model.ActivityResult;
import com.academy.outdooractivity.model.NotificationRule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DummyNotificationService implements NotificationService {

    private final NotificationEvaluator evaluator;

    public DummyNotificationService(NotificationEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public void sendNotification(List<ActivityResult> results, NotificationRule rule) {
        List<ActivityResult> filteredResults = evaluator.filterForNotification(results, rule);
        if (filteredResults.isEmpty()) {
            System.out.println("Skip notification.");
            return;
        }
        System.out.println("Dummy email service activated");
        System.out.println("To: " + rule.email());
    }
}