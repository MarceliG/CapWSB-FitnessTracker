package com.capgemini.wsb.fitnesstracker.training.internal;

import java.util.Date;

import com.capgemini.wsb.fitnesstracker.user.api.User;

public record TrainingDto(
                Long id,
                User user,
                Date startTime,
                Date endTime,
                ActivityType activityType,
                double distance,
                double averageSpeed) {
}
