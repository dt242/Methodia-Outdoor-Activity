package com.academy.outdooractivity.service;

import com.academy.outdooractivity.config.WeatherApiProperties;
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

    public String getForecastJson() {
        String url = properties.getBaseUrl()
                + "/forecast.json?key=" + properties.getApiKey()
                + "&q=Sofia&days=2";

        return restClient.get()
                .uri(url)
                .retrieve()
                .body(String.class);
    }
}