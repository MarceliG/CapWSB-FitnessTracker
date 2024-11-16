package com.capgemini.wsb.fitnesstracker.statistics.internal;

public record StatisticsAddDto(
    Long userId,
    int totalTrainings,
    double totalDistance,
    int totalCaloriesBurned
) {
}
