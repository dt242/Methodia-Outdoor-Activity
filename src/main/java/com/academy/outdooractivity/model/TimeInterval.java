package com.academy.outdooractivity.model;

public record TimeInterval(
        int startHour,
        int endHour,
        boolean isCloudPreferred
) {
    @Override
    public String toString() {
        String base = String.format("%02d-%02d", startHour, endHour);
        return isCloudPreferred ? base + " (preferred cloud)" : base;
    }
}