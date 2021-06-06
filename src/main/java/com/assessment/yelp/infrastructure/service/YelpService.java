package com.assessment.yelp.infrastructure.service;

import com.assessment.yelp.abstraction.IYelpMapper;
import com.assessment.yelp.abstraction.IYelpService;
import com.assessment.yelp.config.ApiKeysConfig;
import com.assessment.yelp.model.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class YelpService implements IYelpService {

    private static final String PARAM_AUTHORIZATION_VALUE = "Bearer ";
    private static final String PARAM_AUTHORIZATION_KEY = "Authorization";
    private static final String PARAM_CACHE_KEY = "Cache-Control";
    private static final String PARAM_CACHE_VALUE = "no-cache";
    private static final String METHOD_REVIEWS = "/reviews";

    private final ApiKeysConfig apiKeysConfig;
    private final RestTemplate restTemplate;
    private final IYelpMapper mapper;

    @Override
    public ArrayList<ReviewResponse> getReviews(String businessName) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(PARAM_AUTHORIZATION_KEY, PARAM_AUTHORIZATION_VALUE.concat(apiKeysConfig.getYelpKey()));
        headers.add(PARAM_CACHE_KEY, PARAM_CACHE_VALUE);

        var uri = buildUri(businessName, METHOD_REVIEWS);

        var httpEntity = new HttpEntity<>(headers);

        var response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);

        return mapper.map(response.getBody());
    }


    private String buildUri(String businessName, String method) {
        final var https = "https://";
        return https.concat(apiKeysConfig.getYelpUrl()).concat(businessName)
                .concat(method);
    }
}
