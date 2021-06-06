package com.assessment.yelp.infrastructure.mapper;

import com.assessment.yelp.abstraction.IYelpMapper;
import com.assessment.yelp.model.ReviewResponse;
import com.assessment.yelp.model.UserModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class YelpMapper implements IYelpMapper {

    @Override
    public ArrayList<ReviewResponse> map(String httpResponse) {
        ArrayList<ReviewResponse> reviews = new ArrayList<>();

        JsonObject jsonResponse = JsonParser.parseString(httpResponse != null ? httpResponse : "{}")
                .getAsJsonObject();
        JsonArray reviewArray = jsonResponse.getAsJsonArray("reviews");

        for (JsonElement review: reviewArray) {
            JsonObject reviewJson = review.getAsJsonObject();
            JsonObject userJson = reviewJson.get("user").getAsJsonObject();

            reviews.add(ReviewResponse.builder()
                    .id(reviewJson.get("id").getAsString())
                    .url(reviewJson.get("url").getAsString())
                    .text(reviewJson.get("text").getAsString())
                    .rating(reviewJson.get("rating").getAsInt())
                    .createdOn(reviewJson.get("time_created").getAsString())
                    .user(UserModel.builder()
                            .id(userJson.getAsJsonObject().get("id").getAsString())
                            .name(userJson.getAsJsonObject().get("name").getAsString())
                            .imageUrl(userJson.getAsJsonObject().get("image_url").isJsonNull() ?
                                            StringUtils.EMPTY :
                                    userJson.getAsJsonObject().get("image_url").getAsString())
                            .profileUrl(userJson.getAsJsonObject().get("profile_url").getAsString())
                            .build())
                    .build());
        }

        return reviews;
    }

}
