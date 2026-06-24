package com.academy.outdooractivity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeatherApiProperties {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.base-url}")
    private String baseUrl;

    @Value("${weather.api.location}")
    private String location;

    @Value("${weather.api.forecast-days}")
    private int forecastDays;

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getLocation() {
        return location;
    }

    public int getForecastDays() {
        return forecastDays;
    }
}