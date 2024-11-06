package com.capgemini.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Component;

import com.capgemini.wsb.fitnesstracker.training.api.Training;

@Component
public class TrainingMapper {

    TrainingDto toDto(Training training) {
        return new TrainingDto(
                training.getId(),
                training.getUser(),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed());
    }

}
