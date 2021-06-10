package com.assessment.yelp.infrastructure.service;

import com.assessment.yelp.abstraction.IYelpMapper;
import com.assessment.yelp.abstraction.IYelpService;
import com.assessment.yelp.config.ApiKeysConfig;
import com.assessment.yelp.enums.Constants;
import com.assessment.yelp.model.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Service class that processes the request and responses
 */
@Service
@RequiredArgsConstructor
public class YelpService implements IYelpService {

    private final ApiKeysConfig apiKeysConfig;
    private final RestTemplate restTemplate;
    private final IYelpMapper mapper;

    /**
     * Retrieves the reviews data of a business
     * @param businessName String Business Name
     * @return ArrayList<{@link ReviewResponse} Review responses with Emotion Data>
     * @throws IOException
     */
    @Override
    public ArrayList<ReviewResponse> getReviews(String businessName) throws IOException {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(Constants.PARAM_AUTHORIZATION_KEY, Constants.PARAM_AUTHORIZATION_VALUE.concat(apiKeysConfig.getYelpKey()));
        headers.add(Constants.PARAM_CACHE_KEY, Constants.PARAM_CACHE_VALUE);

        var uri = buildUri(businessName, Constants.METHOD_REVIEWS);

        var httpEntity = new HttpEntity<>(headers);

        var response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);

        return mapper.map(response.getBody());
    }

    /**
     * Method that creates the url
     * @param businessName String Business Name
     * @param method String API method
     * @return
     */
    private String buildUri(String businessName, String method) {
        final var https = "https://";
        return https.concat(apiKeysConfig.getYelpUrl()).concat(businessName)
                .concat(method);
    }
}
