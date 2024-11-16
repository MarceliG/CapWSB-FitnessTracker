package com.capgemini.wsb.fitnesstracker.statistics.internal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsServiceImpl statisticsService;
    private final StatisticsMapper statisticsMapper;
    private final UserServiceImpl userService;

    /**
     * Retrieves statistics for user.
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public List<StatisticsDto> getStatisticsForUser(@PathVariable Long userId) {
        return statisticsService.findAllStatisticsByUserId(userId)
                .stream()
                .map(statisticsMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Create statistics for user.
     * @param statisticsAddDto
     * @return
     * @throws InterruptedException
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Statistics createStatistics(@RequestBody StatisticsAddDto statisticsAddDto) throws InterruptedException {
        Optional<User> user = userService.getUser(statisticsAddDto.userId());

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Statistics statistic = statisticsMapper.toAdd(user.get(), statisticsAddDto);
        
        return statisticsService.createStatistics(statistic);
    }

    /**
     * Update statistics for user.
     * @param statisticsId
     * @param statisticsAddDto
     * @return
     */
    @PutMapping("/{statisticsId}")
    public Statistics updateStatistics(@PathVariable Long statisticsId, @RequestBody StatisticsAddDto statisticsAddDto) {
        Optional<Statistics> statistics = statisticsService.getStatistics(statisticsId);
        if(statistics.isEmpty()) {
            throw new RuntimeException("Statistics not found");
        }

        Optional<User> user = userService.getUser(statisticsAddDto.userId());
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Statistics updateStatistics = statisticsMapper.toUpdate(user.get(), statistics.get(), statisticsAddDto);

        return statisticsService.updateStatistics(updateStatistics);
    }

    /**
     * Delete statistics.
     * @param statisticsId
     */
    @DeleteMapping("/{statisticsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStatistics(@PathVariable Long statisticsId) {
        Optional<Statistics> statistics = statisticsService.getStatistics(statisticsId);
        if(statistics.isEmpty()) {
            throw new RuntimeException("Statistics not found");
        }

        statisticsService.deleteStatistics(statistics.get());
    }
    
    /**
     * Retrieves statistics with higher calories.
     * @param calories
     * @return
     */
    @GetMapping("/calories/{calories}")
    public List<StatisticsDto> getStatisticsByHigherCalories(@PathVariable int calories) {
        return statisticsService.findAllStatisticsByCalories(calories)
                .stream()
                .map(statisticsMapper::toDto)
                .collect(Collectors.toList());
    }
}
