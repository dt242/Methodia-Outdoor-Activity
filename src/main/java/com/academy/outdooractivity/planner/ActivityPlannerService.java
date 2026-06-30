package com.academy.outdooractivity.planner;

import com.academy.outdooractivity.model.ActivityResult;
import com.academy.outdooractivity.model.DayResult;
import com.academy.outdooractivity.model.SportRule;
import com.academy.outdooractivity.model.TimeInterval;
import com.academy.outdooractivity.model.UserRequest;
import com.academy.outdooractivity.model.WeatherHour;
import com.academy.outdooractivity.weather.WeatherApiClient;
import com.academy.outdooractivity.weather.dto.ForecastResponse;
import com.academy.outdooractivity.util.DateUtils;
import com.academy.outdooractivity.weather.WeatherMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class ActivityPlannerService {

    private final WeatherApiClient weatherApiClient;
    private final IntervalFinder intervalFinder;

    public ActivityPlannerService(WeatherApiClient weatherApiClient,
                                  IntervalFinder intervalFinder) {
        this.weatherApiClient = weatherApiClient;
        this.intervalFinder = intervalFinder;
    }

    public List<ActivityResult> findSuitableActivities(UserRequest request) {
        List<ActivityResult> results = new ArrayList<>();
        Map<String, SportRule> rules = request.sports();
        ForecastResponse response;
        try {
            response = weatherApiClient.getForecast(request.location(), request.forecastDays());
        } catch (Exception e) {
            System.err.println("Unable to retrieve forecast: " + e.getMessage());
            return results;
        }
        List<WeatherHour> weatherHours = WeatherMapper.map(response);
        Map<LocalDate, List<WeatherHour>> weatherByDate = weatherHours.stream()
                .collect(Collectors.groupingBy(
                        hour -> hour.time().toLocalDate(),
                        TreeMap::new,
                        Collectors.toList()
                ));
        rules.forEach((sportName, rule) -> {
            List<DayResult> dayResults = calculateSportReport(rule, weatherByDate);
            results.add(new ActivityResult(sportName, dayResults));
        });
        return results;
    }

    private List<DayResult> calculateSportReport(SportRule rule, Map<LocalDate, List<WeatherHour>> weatherByDate) {
        List<DayResult> dayResults = new ArrayList<>();

        for (Map.Entry<LocalDate, List<WeatherHour>> entry : weatherByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<WeatherHour> hoursForDay = entry.getValue();
            List<TimeInterval> rawIntervals = intervalFinder.findSuitableIntervals(hoursForDay, rule);
            if (!rawIntervals.isEmpty()) {
                boolean preferredWeekend = rule.preferWeekend() && DateUtils.isWeekend(date);
                dayResults.add(new DayResult(date, rawIntervals, preferredWeekend));
            }
        }

        return dayResults;
    }
}