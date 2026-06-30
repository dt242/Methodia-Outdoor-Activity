package com.academy.outdooractivity.service;

import com.academy.outdooractivity.model.ActivityResult;
import com.academy.outdooractivity.model.DayResult;
import com.academy.outdooractivity.model.NotificationRule;
import com.academy.outdooractivity.model.TimeInterval;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class SmtpEmailService implements NotificationService {

    private final JavaMailSender mailSender;

    public SmtpEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

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
                System.out.println("Skip email. No valid intervals.");
                return;
            }
        }

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

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(rule.email());
            message.setSubject("🏃 Perfect weather for your sports!");
            message.setText(emailBody.toString());

            mailSender.send(message);
            System.out.println("Sent email to " + rule.email());
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}