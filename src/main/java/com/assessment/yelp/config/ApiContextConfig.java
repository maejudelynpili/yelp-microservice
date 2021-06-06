package com.assessment.yelp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApiContextConfig {

    private final ApiKeysConfig apiKeysConfig;

}
