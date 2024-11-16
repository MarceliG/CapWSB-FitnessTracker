package com.capgemini.wsb.fitnesstracker.statistics.internal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsProvider;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsService;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class StatisticsServiceImpl implements StatisticsService, StatisticsProvider {
    private final StatisticsRepository statisticsRepository;
    private final TrainingServiceImpl trainingService;
    
    /**
     * Retrieves a statistics based on their ID.
     * @param statisticsId
     * @return Optional<Statistics>
     */
    public Optional<Statistics> getStatistics(Long statisticsId) {
        return statisticsRepository.findById(statisticsId);
    }
    
    /**
     * Count all trainings for user with given ID.
     * 
     * @param userId
     * @return Long
     */
    public long countTrainingsByUserId(final Long userId) {
        return trainingService.findAllTrainingForCurrentMonthByUserId(userId).size();
    }

    /**
     * Retrieves statistics for user.
     * @param userId
     * @return List<Statistics>
     */
    public List<Statistics> findAllStatisticsByUserId(final Long userId) {
        return statisticsRepository.findAll().stream()
                .filter(statistics -> statistics.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    /**
     * Create statistics for user.
     * @param newStatistics
     * @return Statistics
     */
    @Override
    public Statistics createStatistics(final Statistics newStatistics) {
        log.info("Creating Statistics {}", newStatistics);
        if(newStatistics.getId() != null) {
            throw new RuntimeException("Statistics already exists");
        }

        return statisticsRepository.save(newStatistics);
    }

    /**
     * Update statistics for user.
     * @param updatedStatistics
     * @return Statistics
     */
    @Override
    public Statistics updateStatistics(final Statistics updatedStatistics) {
        log.info("Updating Statistics {}", updatedStatistics);
        if(updatedStatistics.getId() == null) {
            throw new RuntimeException("Statistics does not exist");
        }

        return statisticsRepository.save(updatedStatistics);
    }

    /**
     * Delete statistics.
     * @param statistics
     * @return void
     */
    @Override
    public void deleteStatistics(final Statistics statistics) {
        log.info("Deleting Statistics {}", statistics);

        statisticsRepository.deleteById(statistics.getId());
    }

    /**
     * Retrieves a list of all statistics by higher calories.
     * @param calories
     * @return
     */
    public List<Statistics> findAllStatisticsByCalories(final int calories) {
        return statisticsRepository.findAll().stream()
                .filter(statistics -> statistics.getTotalCaloriesBurned() > calories)
                .collect(Collectors.toList());
    }
}
