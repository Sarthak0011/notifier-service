package com.sarthaksolanki11.notifier.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarthaksolanki11.notifier.dispatcher.NotificationDispatcher;
import com.sarthaksolanki11.notifier.events.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationDispatcher notificationDispatcher;

    @KafkaListener(topics = "user-events", groupId = "notification-service")
    public void listen(Event message) {
        try {
//            Event event = objectMapper.readValue(message, Event.class);
            notificationDispatcher.dispatch(message);
        } catch (Exception e) {
            log.error("Failed to consume event: " + e.getMessage());
        }
    }
}
