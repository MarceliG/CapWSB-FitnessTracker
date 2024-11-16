package com.capgemini.wsb.fitnesstracker.statistics.api;

public interface StatisticsService {
    Statistics createStatistics(final Statistics statistics);

    Statistics updateStatistics(final Statistics statistics);

    void deleteStatistics(final Statistics statistics);
}
