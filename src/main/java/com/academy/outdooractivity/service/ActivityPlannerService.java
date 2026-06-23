package com.academy.outdooractivity.service;

import com.academy.outdooractivity.model.SportRule;
import com.academy.outdooractivity.model.WeatherHour;
import com.academy.outdooractivity.model.dto.ForecastResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
            System.err.println("Error loading forecast.");
            return;
        }

        List<WeatherHour> weatherHours = weatherMapper.map(response);
        Map<LocalDate, List<WeatherHour>> weatherByDate = weatherHours.stream()
                .collect(Collectors.groupingBy(
                        hour -> hour.time().toLocalDate()
                ));

        for (Map.Entry<String, SportRule> sportEntry : rules.entrySet()) {
            String sportName = sportEntry.getKey();
            SportRule rule = sportEntry.getValue();
            System.out.printf("%n=== %s ===%n", sportName.toUpperCase());
            weatherByDate.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(dayEntry -> {
                        LocalDate date = dayEntry.getKey();
                        List<WeatherHour> hoursForDay = dayEntry.getValue();
                        List<String> intervals = intervalFinder.findSuitableIntervals(hoursForDay, rule);
                        if (!intervals.isEmpty()) {
                            System.out.println(date + " -> " + intervals);
                        }
                    });
        }
    }
}