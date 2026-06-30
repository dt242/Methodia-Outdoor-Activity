package com.academy.outdooractivity.weather;

import com.academy.outdooractivity.config.WeatherApiProperties;
import com.academy.outdooractivity.weather.dto.ForecastResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class WeatherApiClient {

    private final RestClient restClient;
    private final WeatherApiProperties properties;

    public WeatherApiClient(RestClient restClient, WeatherApiProperties properties) {
        this.restClient = restClient;
        this.properties = properties;
    }

    public ForecastResponse getForecast(String location, int forecastDays) {
        URI uri = UriComponentsBuilder.fromUriString(properties.getBaseUrl())
                .path("/forecast.json")
                .queryParam("key", properties.getKey())
                .queryParam("q", location)
                .queryParam("days", forecastDays)
                .build()
                .toUri();

        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(ForecastResponse.class);
    }
}