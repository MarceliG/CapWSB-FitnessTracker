package com.capgemini.wsb.fitnesstracker.mail.internal;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailService;
import com.capgemini.wsb.fitnesstracker.statistics.internal.StatisticsServiceImpl;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmailServiceImpl implements EmailSender, EmailService {
    private final JavaMailSender mailSender;

    private final UserServiceImpl userService;
    private final TrainingServiceImpl trainingService;
    private final StatisticsServiceImpl statisticsService;

    @Override
    public void sendSummaries() {
        // Download all users
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            log.info("No users found for sending e-mail.");
            return;
        }

        for (User user : users) {
            List<Training> trainingsCurrentMonth = trainingService.findAllTrainingForCurrentMonthByUserId(user.getId());
            List<Training> allTrainings = trainingService.findAllTrainingsByUserId(user.getId());

            String subject = "Summary Report";
            StringBuilder content = new StringBuilder();

            // Statistics from all training sessions
            int totalTrainings = allTrainings.size();
            double totalDistance = allTrainings.stream().mapToDouble(Training::getDistance).sum();
            // Statistics from this month's training sessions
            int currentMonthTrainings = trainingsCurrentMonth.size();
            double currentMonthDistance = trainingsCurrentMonth.stream().mapToDouble(Training::getDistance).sum();

            // Content of the e-mail
            content.append(String.format("Hello %s, \n\n", user.getFirstName()));
            content.append(String.format("All trainings summary:\n"));
            content.append(
                    String.format("\tTotal trainings: %d\n\tTotal distance: %.2f km\n", totalTrainings,
                            totalDistance));

            // All trainings summary
            content.append(String.format("\nAll trainings details:\n"));
            if (allTrainings.isEmpty()) {
                content.append("\tNo trainings completed yet.\n");
            } else {
                for (Training training : allTrainings) {
                    content.append(String.format(
                            "\tTraining: %s\n\tDate: %s - %s\n\tActivity type: %s\n\tDistance: %.2f km\n\tAverage speed: %.2f km/h\n\n",
                            training.getActivityType(), training.getStartTime(), training.getEndTime(),
                            training.getActivityType(), training.getDistance(), training.getAverageSpeed()));
                }
            }

            // Current month training summary
            content.append(String.format("Current month training summary:\n"));
            if (trainingsCurrentMonth.isEmpty()) {
                subject += " - No Training This Month";
                content.append(String.format("\tTotal trainings: 0\n\n"));
                content.append(
                        "It seems that you haven't completed any training this month. Please keep up with your fitness goals!");
            } else {
                content.append(String.format("Total trainings: %d\n\tTotal distance: %.2f km\n\n",
                        currentMonthTrainings, currentMonthDistance));
                for (Training training : trainingsCurrentMonth) {
                    content.append(String.format(
                            "Training: %s\nDate: %s - %s\nActivity type: %s\nDistance: %.2f km\nAverage speed: %.2f km/h\n",
                            training.getActivityType(), training.getStartTime(), training.getEndTime(),
                            training.getActivityType(), training.getDistance(), training.getAverageSpeed()));
                }
            }
            content.append("\n\nBest regards,\nYour Fitness Tracker Team\n");
            String finalContent = content.toString();
            EmailDto emailDto = new EmailDto(user.getEmail(), subject, finalContent);

            // Sending an email
            log.info("Sending email to {}", user.getEmail());
            sendEmail(emailDto);
            log.info("Email sent to {}", user.getEmail());
        }
    }

    @Override
    public void sendEmail(EmailDto emailDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        try {
            mailMessage.setFrom("test@mail.com");
            mailMessage.setTo(emailDto.toAddress());
            mailMessage.setSubject(emailDto.subject());
            mailMessage.setText(emailDto.content());

            mailSender.send(mailMessage);
            log.info("Email sent to {}", emailDto.toAddress());

        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", emailDto.toAddress(), e.getMessage());
        }
    }
}
