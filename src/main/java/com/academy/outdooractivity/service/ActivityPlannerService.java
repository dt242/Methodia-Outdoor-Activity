package com.academy.outdooractivity.service;

import com.academy.outdooractivity.model.DayResult;
import com.academy.outdooractivity.model.SportRule;
import com.academy.outdooractivity.model.TimeInterval;
import com.academy.outdooractivity.model.WeatherHour;
import com.academy.outdooractivity.model.dto.ForecastResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class ActivityPlannerService {

    private final WeatherApiClient weatherApiClient;
    private final WeatherMapper weatherMapper;
    private final SportsConfigLoader configLoader;
    private final IntervalFinder intervalFinder;

    public ActivityPlannerService(WeatherApiClient weatherApiClient,
                                  WeatherMapper weatherMapper,
                                  SportsConfigLoader configLoader,
                                  IntervalFinder intervalFinder) {
        this.weatherApiClient = weatherApiClient;
        this.weatherMapper = weatherMapper;
        this.configLoader = configLoader;
        this.intervalFinder = intervalFinder;
    }

    public void printSuitableActivities() {
        Map<String, SportRule> rules = configLoader.loadRules();
        ForecastResponse response;
        try {
            response = weatherApiClient.getForecast();
        } catch (Exception e) {
            System.err.println("Unable to retrieve forecast: " + e.getMessage());
            return;
        }
        List<WeatherHour> weatherHours = weatherMapper.map(response);
        Map<LocalDate, List<WeatherHour>> weatherByDate = weatherHours.stream()
                .collect(Collectors.groupingBy(
                        hour -> hour.time().toLocalDate(),
                        TreeMap::new,
                        Collectors.toList()
                ));
        rules.forEach((sportName, rule) -> printSportReport(sportName, rule, weatherByDate));
    }

    private void printSportReport(String sportName, SportRule rule, Map<LocalDate, List<WeatherHour>> weatherByDate) {
        System.out.printf("%n=== %s ===%n", sportName.toUpperCase());
        boolean[] hasAnyIntervals = {false};
        weatherByDate.forEach((date, hoursForDay) -> {
            List<TimeInterval> rawIntervals = intervalFinder.findSuitableIntervals(hoursForDay, rule);
            if (!rawIntervals.isEmpty()) {
                hasAnyIntervals[0] = true;
                boolean preferredWeekend = rule.preferWeekend() && isWeekend(date);
                DayResult dayResult = new DayResult(date, rawIntervals, preferredWeekend);
                System.out.printf("%nDate: %s", dayResult.date());
                if (dayResult.preferredWeekend()) {
                    System.out.print(" (preferred weekend)");
                }
                System.out.println("\nIntervals:");
                dayResult.intervals().forEach(interval ->
                        System.out.println("  - " + interval)
                );
            }
        });

        if (!hasAnyIntervals[0]) {
            System.out.println("No suitable intervals found.");
        }
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().getValue() >= 6;
    }
}