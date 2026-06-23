package com.academy.outdooractivity.model;

import java.time.LocalDateTime;

public record WeatherHour(
        LocalDateTime time,
        double temperature,
        double gustKph,
        int chanceOfRain,
        int cloudCover,
        boolean isDaylight
) {}