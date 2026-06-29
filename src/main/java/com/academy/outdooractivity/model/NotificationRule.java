package com.academy.outdooractivity.model;

public record NotificationRule(
        String email,
        boolean weekendOnly
) {}