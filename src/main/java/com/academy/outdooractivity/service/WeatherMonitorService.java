package com.academy.outdooractivity.service;

import com.academy.outdooractivity.model.ActivityResult;
import com.academy.outdooractivity.model.UserRequest;
import com.academy.outdooractivity.ui.ConsolePrinter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherMonitorService {

    private final ActivityPlannerService plannerService;
    private final ConsolePrinter consolePrinter;
    private final NotificationService notificationService;
    private final SportsConfigLoader configLoader;

    public WeatherMonitorService(ActivityPlannerService plannerService,
                                 ConsolePrinter consolePrinter,
                                 NotificationService notificationService,
                                 SportsConfigLoader configLoader) {
        this.plannerService = plannerService;
        this.consolePrinter = consolePrinter;
        this.notificationService = notificationService;
        this.configLoader = configLoader;
    }

    @Scheduled(cron = "${weather.monitor.cron}")
    public void checkWeatherPeriodically() {
        UserRequest request = configLoader.loadConfig();
        List<ActivityResult> results = plannerService.findSuitableActivities();
        consolePrinter.print(results);
        notificationService.notify(results, request.notification());
    }
}