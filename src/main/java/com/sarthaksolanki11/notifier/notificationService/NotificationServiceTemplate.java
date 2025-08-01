package com.sarthaksolanki11.notifier.notificationService;

import com.sarthaksolanki11.notifier.events.Event;
import org.springframework.stereotype.Service;

@Service
public interface NotificationServiceTemplate {
    boolean supports(Event event);
    String render(Event event);
}
