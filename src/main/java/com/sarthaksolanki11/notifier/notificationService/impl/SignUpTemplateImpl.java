package com.sarthaksolanki11.notifier.notificationService.impl;

import com.sarthaksolanki11.notifier.events.Event;
import com.sarthaksolanki11.notifier.notificationService.SignUpTemplate;
import org.springframework.stereotype.Service;

@Service
public class SignUpTemplateImpl implements SignUpTemplate {
    @Override
    public boolean supports(Event event) {
        return "UserSignUp".equals(event.getEvent());
    }

    @Override
    public String render(Event event) {
        return String.format("Welcome aboard: %s",
                event.getMetadata().getOrDefault("user", "Unknown"));
    }
}
