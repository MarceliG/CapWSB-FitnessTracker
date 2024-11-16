package com.capgemini.wsb.fitnesstracker.mail.internal;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Configuration of the {@link EmailSender} (additional to the Spring mail
 * configuration for {@link JavaMailSender} bean autoconfiguration).
 */
@ConfigurationProperties(prefix = "mail")
@Getter
@Setter
@RequiredArgsConstructor
class MailProperties {

    /**
     * Email address that the email should be sent from.
     */
    private final String from;
    private final String host;
    private final Integer port;
    private final String username;
    private final String password;
}
