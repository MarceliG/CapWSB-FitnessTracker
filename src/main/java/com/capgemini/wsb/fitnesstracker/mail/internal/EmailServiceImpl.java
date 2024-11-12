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

        if(users.isEmpty()) {
            return;
        }

        for(User user : users) {
            List<Training> trainings = trainingService.findAllTrainingForCurrentMonthByUserId(user.getId());

            // If the user has no trainings, skip
            if(trainings.isEmpty()) {
                continue;
            }

            // Count all trainings
            Long countTrainings = statisticsService.countTrainingsByUserId(user.getId());

            String subject = "Montly summary report";

            String content = "";

            // Montly list of trainings
            for (Training training : trainings) {
                content += "\n\n";

                content += String.format(
                    "Training: %s\n Date: %s - %s\n Activity type: %s\n Distance: %.2f\n Average_speed: %.2f\n"
                    , training.getActivityType()
                    , training.getStartTime()
                    , training.getEndTime()
                    , training.getActivityType()
                    , training.getDistance()
                    , training.getAverageSpeed()
                );
            }

            // All registered trainings
            content += "\n\n";
            content += String.format(
                "All registered trainings: %d\n",
                countTrainings
            );

            EmailDto emailDto = new EmailDto(user.getEmail(), subject, content);

            log.info("Sending email to {}", user.getEmail());
            sendEmail(emailDto);
            log.info("Email sent to {}", user.getEmail());
        }
    }

    @Override
    public void sendEmail(EmailDto emailDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(emailDto.toAddress());
        mailMessage.setSubject(emailDto.subject());
        mailMessage.setText(emailDto.content());

        mailSender.send(mailMessage);
    }    
}
