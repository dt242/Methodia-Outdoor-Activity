package com.academy.outdooractivity.model;

import java.time.LocalDateTime;

public record WeatherHour(
        LocalDateTime time,
        double temperature,
        double windKph,
        int chanceOfRain,
        int cloudCover,
        boolean isDaylight
) {}