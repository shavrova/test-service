package com.tms.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;


public class ResourceUtil {
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    @SneakyThrows
    public static <T> T createObjectFromJson(String jsonPath, Class<T> clazz) {
        String request = loadResourceAsString(jsonPath);
        return objectMapper.readValue(request, clazz);
    }

    @SneakyThrows
    public static String loadResourceAsString(String resourcePath) {
        InputStream inputStream = new ClassPathResource(resourcePath).getInputStream();
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining());
    }

    @SneakyThrows
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
