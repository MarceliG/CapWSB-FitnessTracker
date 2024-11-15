package com.capgemini.wsb.fitnesstracker.statistics.internal;

import org.springframework.stereotype.Component;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.user.api.User;

@Component
public class StatisticsMapper {

    /**
     * Converts a Statistics entity to a StatisticsDto.
     * @param statistics
     * @return
     */
    public StatisticsDto toDto(Statistics statistics) {
        return new StatisticsDto(
                statistics.getId(),
                statistics.getUser(),
                statistics.getTotalTrainings(),
                statistics.getTotalDistance(),
                statistics.getTotalCaloriesBurned());
    }

    /**
     * Converts a StatisticsAddDto entity to a Statistics.
     * @param user
     * @param statisticsAddDto
     * @return
     */
    public Statistics toAdd(User user, StatisticsAddDto statisticsAddDto) {
        return new Statistics(
                user,
                statisticsAddDto.totalTrainings(),
                statisticsAddDto.totalDistance(),
                statisticsAddDto.totalCaloriesBurned());
    }

    /**
     * Update Statistics for user.
     * @param statistics
     * @return
     */
    public Statistics toUpdate(User user, Statistics statistics, StatisticsAddDto statisticsAddDto) {
            statistics.setUser(user);
            statistics.setTotalTrainings(statisticsAddDto.totalTrainings());
            statistics.setTotalDistance(statisticsAddDto.totalDistance());
            statistics.setTotalCaloriesBurned(statisticsAddDto.totalCaloriesBurned());
            return statistics;
    }
}
