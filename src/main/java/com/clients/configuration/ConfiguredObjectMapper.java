package com.clients.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public final class ConfiguredObjectMapper {
    private static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

    private final ObjectMapper mapper = new ObjectMapper();

    public ConfiguredObjectMapper() {
        mapper.setDateFormat(new SimpleDateFormat(DATE_TIME_FORMAT));
    }

    public ObjectMapper getObjectMapper() {
        return mapper;
    }
}
