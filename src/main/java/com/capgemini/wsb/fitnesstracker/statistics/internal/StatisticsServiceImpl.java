package com.capgemini.wsb.fitnesstracker.statistics.internal;

import java.util.Optional;

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
    
    public Optional<Statistics> getStatistics(Long statisticsId) {
        return statisticsRepository.findById(statisticsId);
    }
    
    /**
     * Count all trainings for user with given ID.
     * 
     * @param userId
     * @return Long
     */
    public long countTrainingsByUserId(Long userId) {
        return trainingService.findAllTrainingForCurrentMonthByUserId(userId).size();
    }
}
