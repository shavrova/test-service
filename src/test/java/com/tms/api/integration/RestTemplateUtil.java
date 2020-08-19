package com.tms.api.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Component
public class RestTemplateUtil {

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> request;

    public <T> T create(String url, String payload, Class<T> clazz) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        request = new HttpEntity<>(payload, headers);
        return restTemplate.postForObject(url, request, clazz);
    }

}
