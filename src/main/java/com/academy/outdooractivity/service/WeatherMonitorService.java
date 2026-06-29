package com.academy.outdooractivity.service;

import com.academy.outdooractivity.model.ActivityResult;
import com.academy.outdooractivity.ui.ConsolePrinter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherMonitorService {

    private final ActivityPlannerService plannerService;
    private final ConsolePrinter consolePrinter;

    public WeatherMonitorService(ActivityPlannerService plannerService, ConsolePrinter consolePrinter) {
        this.plannerService = plannerService;
        this.consolePrinter = consolePrinter;
    }

    @Scheduled(cron = "${weather.monitor.cron}")
    public void checkWeatherPeriodically() {
        List<ActivityResult> results = plannerService.findSuitableActivities();
        consolePrinter.print(results);
    }
}