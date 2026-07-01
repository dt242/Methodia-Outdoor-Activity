package com.academy.outdooractivity.service;

import com.academy.outdooractivity.calendar.CalendarEventMapper;
import com.academy.outdooractivity.calendar.CalendarService;
import com.academy.outdooractivity.model.ActivityResult;
import com.academy.outdooractivity.model.UserRequest;
import com.academy.outdooractivity.notification.NotificationService;
import com.academy.outdooractivity.planner.ActivityPlannerService;
import com.academy.outdooractivity.ui.ConsolePrinter;
import com.google.api.services.calendar.model.Event;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherMonitorService {

    private final ActivityPlannerService plannerService;
    private final ConsolePrinter consolePrinter;
    private final NotificationService notificationService;
    private final SportsConfigLoader configLoader;
    private final CalendarService calendarService;
    private final CalendarEventMapper calendarEventMapper;


    public WeatherMonitorService(ActivityPlannerService plannerService,
                                 ConsolePrinter consolePrinter,
                                 NotificationService notificationService,
                                 SportsConfigLoader configLoader, CalendarService calendarService, CalendarEventMapper calendarEventMapper) {
        this.plannerService = plannerService;
        this.consolePrinter = consolePrinter;
        this.notificationService = notificationService;
        this.configLoader = configLoader;
        this.calendarService = calendarService;
        this.calendarEventMapper = calendarEventMapper;
    }

    @Scheduled(cron = "${weather.monitor.cron}")
    public void checkWeatherPeriodically() {
        UserRequest request = configLoader.loadConfig();
        List<ActivityResult> results = plannerService.findSuitableActivities(request);
        consolePrinter.print(results);
        notificationService.sendNotification(results, request.notification());
        List<Event> calendarEvents = calendarEventMapper.mapToEvents(results);
        calendarService.createEvents(calendarEvents);
    }
}