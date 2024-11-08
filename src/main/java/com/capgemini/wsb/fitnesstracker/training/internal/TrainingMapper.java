package com.capgemini.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Component;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;

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

    public Training toAdd(User user, TrainingAddDto trainingAddDto) {
        return new Training(
            user,
            trainingAddDto.startTime(),
            trainingAddDto.endTime(),
            trainingAddDto.activityType(),
            trainingAddDto.distance(),
            trainingAddDto.averageSpeed()
        );
    }

    public Training toUpdate(User user, Training training, TrainingAddDto trainingAddDto) {
        training.setUser(user);
        training.setStartTime(trainingAddDto.startTime());
        training.setEndTime(trainingAddDto.endTime());
        training.setActivityType(trainingAddDto.activityType());
        training.setDistance(trainingAddDto.distance());
        training.setAverageSpeed(trainingAddDto.averageSpeed());
        return training;
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
