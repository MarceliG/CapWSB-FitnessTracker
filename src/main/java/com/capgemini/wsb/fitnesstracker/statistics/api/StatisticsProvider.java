package com.capgemini.wsb.fitnesstracker.statistics.api;

import java.util.List;
import java.util.Optional;

public interface StatisticsProvider {

    /**
     * Retrieves a statistics based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param statisticsId id of the statistics to be searched
     * @return An {@link Optional} containing the located Statistics, or {@link Optional#empty()} if not found
     */
    Optional<Statistics> getStatistics(Long statisticsId);
    
    /**
     * Count all trainings for user with given ID.
     * 
     * @param userId
     * @return Long
     */
    long countTrainingsByUserId(Long userId);

    /**
     * Retrieves statistics for user.
     * @param userId
     * @return List<Statistics>
     */
    List<Statistics> findAllStatisticsByUserId(final Long userId);
}
