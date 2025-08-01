package com.sarthaksolanki11.notifier.notificationService.impl;

import com.sarthaksolanki11.notifier.events.Event;
import com.sarthaksolanki11.notifier.notificationService.LoginTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginTemplateImpl implements LoginTemplate {
    @Override
    public boolean supports(Event event) {
        return "UserLogin".equals(event.getEvent());
    }

    @Override
    public String render(Event event) {
        return String.format("Login detected for username: %s at time: %s",
                event.getMetadata().getOrDefault("user", "Unknown"),
                event.getTimestamp());
    }
}
