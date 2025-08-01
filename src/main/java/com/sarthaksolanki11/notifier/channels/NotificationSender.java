package com.sarthaksolanki11.notifier.channels;

import com.sarthaksolanki11.notifier.enums.NotificationChannels;
import com.sarthaksolanki11.notifier.events.Event;
import org.springframework.stereotype.Service;

@Service
public interface NotificationSender {
    boolean supports(NotificationChannels channel);
    void send(String message, Event event);
}
