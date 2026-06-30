package com.academy.outdooractivity.notification;

import com.academy.outdooractivity.model.ActivityResult;
import com.academy.outdooractivity.model.NotificationRule;
import java.util.List;

public interface NotificationService {
    void sendNotification(List<ActivityResult> results, NotificationRule rule);
}