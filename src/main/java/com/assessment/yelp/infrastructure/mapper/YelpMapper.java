package com.assessment.yelp.infrastructure.mapper;

import com.assessment.yelp.abstraction.IGoogleVisionService;
import com.assessment.yelp.abstraction.IYelpMapper;
import com.assessment.yelp.model.EmotionModel;
import com.assessment.yelp.model.ReviewListResponse;
import com.assessment.yelp.model.ReviewResponse;
import com.assessment.yelp.model.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class that maps the values retrieved from external APIs
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class YelpMapper implements IYelpMapper {

    private final IGoogleVisionService googleVisionService;
    private final ObjectMapper objectMapper;

    /**
     * Method that maps the values of the External API response to the JSON response
     * @param httpResponse String Response from the Yelp API
     * @return ArrayList<{@link ReviewResponse} Response with Emotion Data>
     * @throws IOException
     */
    @Override
    public ArrayList<ReviewResponse> map(String httpResponse) throws IOException {
        ArrayList<ReviewResponse> reviews = new ArrayList<>();

        ReviewListResponse reviewListResponse = objectMapper.readValue(httpResponse, ReviewListResponse.class);
        JsonObject jsonResponse = JsonParser.parseString(httpResponse != null ? httpResponse : "{}")
                .getAsJsonObject();
        try {
            JsonArray reviewArray = jsonResponse.getAsJsonArray("reviews");

            for (JsonElement review: reviewArray) {
                JsonObject reviewJson = review.getAsJsonObject();
                JsonObject userJson = reviewJson.get("user").getAsJsonObject();
                String imageUrl = userJson.getAsJsonObject().get("image_url").isJsonNull() ?
                        StringUtils.EMPTY :
                        userJson.getAsJsonObject().get("image_url").getAsString();


                reviews.add(ReviewResponse.builder()
                        .id(reviewJson.get("id").getAsString())
                        .url(reviewJson.get("url").getAsString())
                        .text(reviewJson.get("text").getAsString())
                        .rating(reviewJson.get("rating").getAsInt())
                        .time_created(reviewJson.get("time_created").getAsString())
                        .user(UserModel.builder()
                                .id(userJson.getAsJsonObject().get("id").getAsString())
                                .name(userJson.getAsJsonObject().get("name").getAsString())
                                .image_url(imageUrl)
                                .emotion(StringUtils.isNotEmpty(imageUrl) ?
                                        EmotionModel.builder()
                                                .emotions(googleVisionService.getEmotionData(imageUrl))
                                                .build():
                                        null)
                                .profile_url(userJson.getAsJsonObject().get("profile_url").getAsString())
                                .build())
                        .build());
            }
        } catch (Exception e) {
            log.info("No reviews found");
        }

        return reviews;
    }

    @Override
    public ReviewListResponse mapper(String httpResponse) throws IOException {

        ReviewListResponse reviewListResponse = objectMapper.readValue(httpResponse, ReviewListResponse.class);
        List<ReviewResponse> reviews = reviewListResponse.getReviews();

        for (ReviewResponse review: reviews) {
            String imageUrl = review.getUser().getImage_url();
            review.getUser().setEmotion(StringUtils.isNotEmpty(imageUrl) ?
                                        EmotionModel.builder()
                                                .emotions(googleVisionService.getEmotionData(imageUrl))
                                                .build():
                                        null);
        }

        return reviewListResponse;
    }

}
