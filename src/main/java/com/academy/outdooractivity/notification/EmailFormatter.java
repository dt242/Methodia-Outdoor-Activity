package com.academy.outdooractivity.notification;

import com.academy.outdooractivity.model.ActivityResult;
import com.academy.outdooractivity.model.DayResult;
import com.academy.outdooractivity.model.TimeInterval;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailFormatter {

    public String buildEmailBody(List<ActivityResult> results) {
        StringBuilder emailBody = new StringBuilder();
        emailBody.append("Hello,\n\nPerfect weather conditions have been found based on your criteria:\n");

        for (ActivityResult result : results) {
            if (!result.hasIntervals()) continue;

            emailBody.append("\n=== ").append(result.sportName().toUpperCase()).append(" ===\n");
            for (DayResult dayResult : result.dayResults()) {
                emailBody.append("Date: ").append(dayResult.date());
                if (dayResult.preferredWeekend()) {
                    emailBody.append(" (preferred weekend)");
                }
                emailBody.append("\nIntervals:\n");
                for (TimeInterval interval : dayResult.intervals()) {
                    emailBody.append("  - ").append(interval).append("\n");
                }
            }
        }
        return emailBody.toString();
    }
}