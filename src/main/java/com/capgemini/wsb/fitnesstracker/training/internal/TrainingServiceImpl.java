package com.capgemini.wsb.fitnesstracker.training.internal;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrainingServiceImpl implements TrainingProvider {
    /**
     * Finds all trainings by specified activity type.
     *
     * @param activityType the activity type to filter trainings by
     * @return a list of trainings with the specified activity type
     */
    @Override
    public List<Training> findByActivityType(ActivityType activityType) {
        return trainingRepository.findAll()
                .stream()
                .filter(training -> training.getActivityType().equals(activityType))
                .collect(Collectors.toList());
    }

    /**
     * Gets the user associated with a specific training.
     *
     * @param trainingId the ID of the training
     * @return an Optional containing the user if found, otherwise empty
     */
    @Override
    public Optional<User> getTraining(final Long trainingId) {
        return trainingRepository.findById(trainingId)
                .map(training -> training.getUser());
    }

    /**
     * Deletes all trainings associated with a specific user.
     *
     * @param userId the ID of the user whose trainings will be deleted
     */
    public void deleteTrainingByUserId(Long userId) {
        trainingRepository.findAll().stream()
                .filter(training -> training.getUser().getId().equals(userId))
                .forEach(trainingRepository::delete);
    }

    /**
     * Retrieves all trainings.
     *
     * @return a list of all trainings
     */
    @Override
    public List<Training> findAllTrainings() {
        return trainingRepository.findAll();
    }

    /**
     * Finds all trainings associated with a specific user.
     *
     * @param userId the ID of the user
     * @return a list of trainings for the specified user
     */
    @Override
    public List<Training> findAllTrainingsByUserId(Long userId) {
        return trainingRepository.findAll().stream()
                .filter(training -> training.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    /**
     * Finds all finished trainings that ended after a specified date.
     *
     * @param afterDate the date to filter finished trainings by
     * @return a list of finished trainings that ended after the specified date
     */
    public List<Training> findAllFinishedTrainingsAfter(Date afterDate) {
        return trainingRepository.findAll()
                .stream()
                .filter(training -> training.getEndTime().after(afterDate))
                .collect(Collectors.toList());
    }

    /**
     * Finds all trainings by specified activity type.
     *
     * @param activityType the activity type to filter trainings by
     * @return a list of trainings with the specified activity type
     */
    public List<Training> findAllTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findAll()
                .stream()
                .filter(training -> training.getActivityType().equals(activityType))
                .collect(Collectors.toList());
    }

    /**
     * Gets a training by its ID.
     *
     * @param trainingId the ID of the training
     * @return an Optional containing the training if found, otherwise empty
     */
    public Optional<Training> getTrainingById(Long trainingId) {
        return trainingRepository.findById(trainingId);
    }

    /**
     * Creates a new training.
     *
     * @param training the training to create
     * @return the created training
     * @throws IllegalArgumentException if the training already has an ID
     */
    public Training createTraining(final Training training) {
        log.info("Creating Training {}", training);
        if (training.getId() != null) {
            throw new IllegalArgumentException("Training has already DB ID, create is not permitted!");
        }
        return trainingRepository.save(training);
    }

    /**
     * Updates an existing training.
     *
     * @param updatedTraining the training to update
     * @return the updated training
     * @throws IllegalArgumentException if the training does not exist in the
     *                                  database
     */
    public Training updateTraining(final Training updatedTraining) {
        log.info("Updating Training {}", updatedTraining);
        if (updatedTraining.getId() == null) {
            throw new IllegalArgumentException("Training not exists in DB, update is not permitted!");
        }
        return trainingRepository.save(updatedTraining);
    }

}