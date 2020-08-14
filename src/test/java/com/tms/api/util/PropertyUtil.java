package com.tms.api.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PropertyUtil {

    @Value("${spring.datasource.username}")
    private String username;


}