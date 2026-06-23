package com.academy.outdooractivity.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ForecastResponse(
        Forecast forecast
) {}