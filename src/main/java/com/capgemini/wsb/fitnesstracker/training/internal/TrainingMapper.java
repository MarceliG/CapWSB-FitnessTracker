package com.capgemini.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Component;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;

@Component
public class TrainingMapper {

    /**
     * Maps a `TrainingDto` to a `Training` entity.
     *
     * @param trainingDto the DTO containing training details
     * @return a `Training` entity based on the provided `TrainingDto`
     */
    public Training toEntity(TrainingDto trainingDto) {
        return new Training(
                trainingDto.user(),
                trainingDto.startTime(),
                trainingDto.endTime(),
                trainingDto.activityType(),
                trainingDto.distance(),
                trainingDto.averageSpeed());
    }

    /**
     * Creates a new `Training` entity based on a user and `TrainingAddDto`.
     *
     * @param user           the user associated with the training
     * @param trainingAddDto the DTO containing details to add a new training
     * @return a new `Training` entity based on the provided user and
     *         `TrainingAddDto`
     */
    public Training toAdd(User user, TrainingAddDto trainingAddDto) {
        return new Training(
                user,
                trainingAddDto.startTime(),
                trainingAddDto.endTime(),
                trainingAddDto.activityType(),
                trainingAddDto.distance(),
                trainingAddDto.averageSpeed());
    }

    /**
     * Updates an existing `Training` entity with data from a user and
     * `TrainingAddDto`.
     *
     * @param user           the user associated with the training
     * @param training       the existing training entity to update
     * @param trainingAddDto the DTO containing the updated details for the training
     * @return the updated `Training` entity with the new data
     */
    public Training toUpdate(User user, Training training, TrainingAddDto trainingAddDto) {
        training.setUser(user);
        training.setStartTime(trainingAddDto.startTime());
        training.setEndTime(trainingAddDto.endTime());
        training.setActivityType(trainingAddDto.activityType());
        training.setDistance(trainingAddDto.distance());
        training.setAverageSpeed(trainingAddDto.averageSpeed());
        return training;
    }

    /**
     * Maps a `Training` entity to a `TrainingDto`.
     *
     * @param training the `Training` entity to map
     * @return a `TrainingDto` representing the given `Training` entity
     */
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
