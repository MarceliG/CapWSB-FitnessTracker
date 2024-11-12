package com.capgemini.wsb.fitnesstracker.mail.internal;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableScheduling
@EnableConfigurationProperties(MailProperties.class)
@RequiredArgsConstructor
@Slf4j
class MailConfig {
    private final EmailServiceImpl emailService;

    @Scheduled(cron = "0 0 0 * * MON")
    public void scheduleFixedRate() {
        log.info("Sending summaries...");
        emailService.sendSummaries();
    }
}
