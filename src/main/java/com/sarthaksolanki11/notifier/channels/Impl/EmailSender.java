package com.sarthaksolanki11.notifier.channels.Impl;

import com.sarthaksolanki11.notifier.channels.NotificationSender;
import com.sarthaksolanki11.notifier.enums.NotificationChannels;
import com.sarthaksolanki11.notifier.events.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSender implements NotificationSender {

    private final JavaMailSender mailSender;

    @Override
    public boolean supports(NotificationChannels channel) {
        return channel == NotificationChannels.EMAIL;
    }

    @Override
    @Retryable(
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public void send(String message, Event event) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String toMail = (String) event.getMetadata().get("email");
        String subject = (String) event.getMetadata().getOrDefault("subject", "Notification");
        String customMessage = (String) event.getMetadata().getOrDefault("message", message);

        mailMessage.setTo(toMail);
        mailMessage.setSubject(subject);
        mailMessage.setText(customMessage);

        mailSender.send(mailMessage);
        log.info("Notification sent successfully");
    }

    @Recover
    public void recover(Exception ex, String message, Event event) {
        log.error(String.format("Email failed for event: %s for user: %s",
                event.getEvent(),
                event.getMetadata().getOrDefault("user", "Unknown")));
        log.error("Reason: " + ex.getMessage());
    }
}
