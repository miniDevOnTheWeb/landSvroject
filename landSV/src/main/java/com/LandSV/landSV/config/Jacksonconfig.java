package com.LandSV.landSV.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Jacksonconfig {
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void setUp () {
        objectMapper.registerModule(new JavaTimeModule());
    }
}
