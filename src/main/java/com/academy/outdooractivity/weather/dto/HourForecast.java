package com.academy.outdooractivity.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HourForecast(
        String time,

        @JsonProperty("temp_c")
        double tempC,

        @JsonProperty("gust_kph")
        double gustKph,

        @JsonProperty("chance_of_rain")
        int chanceOfRain,

        @JsonProperty("cloud")
        int cloudCover,

        @JsonProperty("is_day")
        int isDay
) {}