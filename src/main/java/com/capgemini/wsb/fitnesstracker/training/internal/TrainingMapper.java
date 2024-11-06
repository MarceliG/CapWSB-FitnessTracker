package com.capgemini.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Component;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.github.database.rider.example.UserRepository;

@Component
public class TrainingMapper {
    private final UserRepository userRepository;

    public TrainingMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Training toEntity(TrainingDto trainingDto) {
        User user = userService.getUser(trainingDto.user().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new Training(
                user,
                trainingDto.startTime(),
                trainingDto.endTime(),
                trainingDto.activityType(),
                trainingDto.distance(),
                trainingDto.averageSpeed());
    }

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
