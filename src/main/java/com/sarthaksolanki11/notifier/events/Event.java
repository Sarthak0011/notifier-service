package com.sarthaksolanki11.notifier.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
public class Event {
    private String event;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant timestamp;

    private List<String> enabledChannels;

    private Map<String, Object> metadata;
}
