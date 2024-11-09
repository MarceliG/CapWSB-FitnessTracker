package com.capgemini.wsb.fitnesstracker.training.internal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;
    private final UserServiceImpl userService;

    /**
     * Retrieves a list of all trainings.
     *
     * @return a list of `TrainingDto` representing all trainings
     */
    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Retrieves a list of trainings for a specific user.
     *
     * @param userId the ID of the user for whom to retrieve trainings
     * @return a list of `TrainingDto` for the specified user
     */
    @GetMapping("/{userId}")
    public List<TrainingDto> getTrainingsForUser(@PathVariable Long userId) {
        return trainingService.findAllTrainingsByUserId(userId)
                .stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of finished trainings after a specific date.
     *
     * @param afterTime the date in "yyyy-MM-dd" format after which to retrieve
     *                  finished trainings
     * @return a list of `TrainingDto` for finished trainings after the specified
     *         date
     * @throws ParseException if the date format is invalid
     */
    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> getFinishedTrainingsAfter(@PathVariable String afterTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date afterDate = sdf.parse(afterTime);
        return trainingService.findAllFinishedTrainingsAfter(afterDate)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Retrieves a list of trainings by activity type.
     *
     * @param activityType the activity type to filter trainings by
     * @return a list of `TrainingDto` for trainings with the specified activity
     *         type
     */
    @GetMapping("/activityType")
    public List<TrainingDto> getAllTrainingByActivityType(@RequestParam String activityType) {
        ActivityType type = ActivityType.valueOf(activityType.toUpperCase());
        return trainingService.findAllTrainingsByActivityType(type)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    /**
     * Adds a new training based on the provided `TrainingAddDto`.
     *
     * @param trainingAddDto the DTO containing details for the new training
     * @return the created `Training` entity
     * @throws InterruptedException if the process is interrupted
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Training addTraining(@RequestBody TrainingAddDto trainingAddDto) throws InterruptedException {
        Optional<User> user = userService.getUser(trainingAddDto.userId());

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Training training = trainingMapper.toAdd(user.get(), trainingAddDto);

        return trainingService.createTraining(training);
    }

    /**
     * Updates an existing training with the provided `TrainingAddDto`.
     *
     * @param trainingId     the ID of the training to update
     * @param trainingAddDto the DTO containing updated training details
     * @return the updated `Training` entity
     */
    @PutMapping("/{trainingId}")
    public Training updateTraining(@PathVariable Long trainingId, @RequestBody TrainingAddDto trainingAddDto) {
        Optional<Training> training = trainingService.getTrainingById(trainingId);
        if (training.isEmpty()) {
            throw new RuntimeException("Training not found");
        }

        Optional<User> user = userService.getUser(trainingAddDto.userId());
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Training updateTrainng = trainingMapper.toUpdate(user.get(), training.get(), trainingAddDto);

        return trainingService.updateTraining(updateTrainng);
    }

}
