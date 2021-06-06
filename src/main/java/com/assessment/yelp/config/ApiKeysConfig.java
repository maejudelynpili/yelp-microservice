package com.assessment.yelp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class ApiKeysConfig {

        @Value("${externalapi.yelp.key}")
        private String yelpKey;

        @Value("${externalapi.yelp.url}")
        private String yelpUrl;

        @Value("${externalapi.google.key}")
        private String googleKey;

        @Value("${externalapi.google.url}")
        private String googleUrl;

}
