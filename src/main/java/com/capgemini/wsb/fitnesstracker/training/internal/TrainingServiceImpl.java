package com.capgemini.wsb.fitnesstracker.training.internal;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;

@Service
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public Optional<User> getTraining(final Long trainingId) {
        return trainingRepository.findById(trainingId)
                .map(training -> training.getUser());
    }

    public void deleteTrainingByUserId(Long userId) {
        trainingRepository.findAll().stream()
                .filter(training -> training.getUser().getId().equals(userId))
                .forEach(trainingRepository::delete);
    }

    @Override
    public List<Training> findAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> findAllTrainingsByUserId(Long userId) {
        return trainingRepository.findAll().stream()
                .filter(training -> training.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Training> findAllFinishedTrainingsAfter(Date afterDate) {
        return trainingRepository.findAll()
                .stream()
                .filter(training -> training.getEndTime().after(afterDate))
                .collect(Collectors.toList());
    }

    public List<Training> findAllTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findAll()
                .stream()
                .filter(training -> training.getActivityType().equals(activityType))
                .collect(Collectors.toList());
    }

    // public Training createTraining(Training training) {
    // return trainingRepository.save(training);
    // }

}