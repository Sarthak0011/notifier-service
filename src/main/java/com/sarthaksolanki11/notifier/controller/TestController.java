package com.sarthaksolanki11.notifier.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarthaksolanki11.notifier.events.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @PostMapping("/send")
    public String sendTest(@RequestBody Event event) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(event);
        kafkaTemplate.send("user-events", message);
        return "Notification sent.";
    }
}
