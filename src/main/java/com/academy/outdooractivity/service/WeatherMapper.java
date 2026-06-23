package com.academy.outdooractivity.service;

import com.academy.outdooractivity.model.WeatherHour;
import com.academy.outdooractivity.model.dto.ForecastResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WeatherMapper {

    public List<WeatherHour> map(ForecastResponse response) {
        return response.forecast()
                .forecastday()
                .stream()
                .flatMap(day -> day.hour().stream())
                .map(hour -> new WeatherHour(
                        LocalDateTime.parse(hour.time().replace(" ", "T")),
                        hour.tempC(),
                        hour.gustKph(),
                        hour.chanceOfRain(),
                        hour.cloudCover(),
                        hour.isDay() == 1
                ))
                .toList();
    }
}