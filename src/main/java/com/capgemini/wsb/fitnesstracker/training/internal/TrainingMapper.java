package com.capgemini.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Component;

import com.capgemini.wsb.fitnesstracker.training.api.Training;

@Component
public class TrainingMapper {
    public Training toEntity(TrainingDto trainingDto) {
        return new Training(
            trainingDto.user(),
            trainingDto.startTime(),
            trainingDto.endTime(),
            trainingDto.activityType(),
            trainingDto.distance(),
            trainingDto.averageSpeed()
        );
    }

    TrainingDto toDto(Training training) {
        return new TrainingDto(
            training.getId(),
            training.getUser(),
            training.getStartTime(),
            training.getEndTime(),
            training.getActivityType(),
            training.getDistance(),
            training.getAverageSpeed()
        );
    }
}
