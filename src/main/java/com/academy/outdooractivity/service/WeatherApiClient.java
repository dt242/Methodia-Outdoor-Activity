package com.academy.outdooractivity.service;

import com.academy.outdooractivity.config.WeatherApiProperties;
import com.academy.outdooractivity.model.dto.ForecastResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class WeatherApiClient {

    private final RestClient restClient;
    private final WeatherApiProperties properties;

    public WeatherApiClient(RestClient restClient, WeatherApiProperties properties) {
        this.restClient = restClient;
        this.properties = properties;
    }

    public ForecastResponse getForecast() {
        String url = properties.getBaseUrl()
                + "/forecast.json?key=" + properties.getApiKey()
                + "&q=" + properties.getLocation()
                + "&days=" + properties.getForecastDays();

        return restClient.get()
                .uri(url)
                .retrieve()
                .body(ForecastResponse.class);
    }
}