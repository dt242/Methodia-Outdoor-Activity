package com.academy.outdooractivity.service;

import com.academy.outdooractivity.model.UserRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SportsConfigLoader {

    private final ObjectMapper objectMapper;

    public SportsConfigLoader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public UserRequest loadConfig() {
        try {
            ClassPathResource resource = new ClassPathResource("sports.json");

            return objectMapper.readValue(
                    resource.getInputStream(),
                    new TypeReference<>() {}
            );
        } catch (IOException e) {
            throw new RuntimeException("Error loading sports.json: " + e.getMessage(), e);
        }
    }
}