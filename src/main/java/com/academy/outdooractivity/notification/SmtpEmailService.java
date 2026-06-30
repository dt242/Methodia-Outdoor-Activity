package com.academy.outdooractivity.notification;

import com.academy.outdooractivity.model.ActivityResult;
import com.academy.outdooractivity.model.NotificationRule;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class SmtpEmailService implements NotificationService {

    private final JavaMailSender mailSender;
    private final NotificationEvaluator evaluator;
    private final EmailFormatter formatter;

    public SmtpEmailService(JavaMailSender mailSender,
                            NotificationEvaluator evaluator,
                            EmailFormatter formatter) {
        this.mailSender = mailSender;
        this.evaluator = evaluator;
        this.formatter = formatter;
    }

    @Override
    public void sendNotification(List<ActivityResult> results, NotificationRule rule) {
        List<ActivityResult> filteredResults = evaluator.filterForNotification(results, rule);
        if (filteredResults.isEmpty()) {
            System.out.println("Skip notification.");
            return;
        }
        String emailBody = formatter.buildEmailBody(filteredResults);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(rule.email());
            message.setSubject("Outdoor Activity Alert");
            message.setText(emailBody);
            mailSender.send(message);
            System.out.println("Sent email to " + rule.email());
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}