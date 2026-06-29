package com.academy.outdooractivity.model;

import java.util.Map;

public record UserRequest(
        String location,
        int forecastDays,
        NotificationRule notification,
        Map<String, SportRule> sports
) {}