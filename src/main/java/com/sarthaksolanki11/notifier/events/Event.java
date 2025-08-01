package com.sarthaksolanki11.notifier.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sarthaksolanki11.notifier.config.MetadataDeserializer;
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

//    @JsonDeserialize(using = MetadataDeserializer.class)
    private Map<String, Object> metadata;
}
