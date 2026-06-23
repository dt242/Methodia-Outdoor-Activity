package com.academy.outdooractivity.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ForecastDay(
        String date,
        List<HourForecast> hour
) {}