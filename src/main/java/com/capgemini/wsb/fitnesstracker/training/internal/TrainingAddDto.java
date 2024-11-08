package com.capgemini.wsb.fitnesstracker.training.internal;

import java.util.Date;

public record TrainingAddDto (
    Long userId,
    Date startTime,
    Date endTime,
    ActivityType activityType,
    double distance,
    double averageSpeed
) {

}