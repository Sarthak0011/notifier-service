package com.sarthaksolanki11.notifier.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Map;

public class MetadataDeserializer extends JsonDeserializer<Map<String, Object>> {

    @Override
    public Map<String, Object> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        return p.readValueAs(Map.class); // deserialize raw JSON object to generic map
    }
}
