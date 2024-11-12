package com.capgemini.wsb.fitnesstracker.mail.internal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.statistics.internal.StatisticsServiceImpl;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;

import static java.util.UUID.randomUUID;

public class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private TrainingServiceImpl trainingService;

    @Mock
    private StatisticsServiceImpl statisticsService;

    @InjectMocks
    private EmailServiceImpl emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendSummaries_UserWithTrainings() throws ParseException {
        User user = generateUser();
        user.setId(1L);
        user.setEmail("user@example.com");

        Training training = generateTraining(user);

        when(userService.findAllUsers()).thenReturn(List.of(user));
        when(trainingService.findAllTrainingForCurrentMonthByUserId(user.getId())).thenReturn(List.of(training));
        when(statisticsService.countTrainingsByUserId(user.getId())).thenReturn(1L);

        emailService.sendSummaries();

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    private static User generateUser() {
        return new User(randomUUID().toString(), randomUUID().toString(), LocalDate.now(), randomUUID().toString());
    }

    private static Training generateTraining(User user) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return new Training(
                user,
                sdf.parse("2023-10-01 10:00:00"),
                sdf.parse("2023-10-01 11:00:00"),
                ActivityType.RUNNING,
                5.0,
                10.0);
    }
}