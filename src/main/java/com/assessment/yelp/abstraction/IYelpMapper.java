package com.assessment.yelp.abstraction;

import com.assessment.yelp.model.ReviewResponse;

import java.util.ArrayList;

public interface IYelpMapper {
    ArrayList<ReviewResponse> map(String httpResponse);
}
