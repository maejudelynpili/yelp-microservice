package com.assessment.yelp.abstraction;

import com.assessment.yelp.model.ReviewListResponse;
import com.assessment.yelp.model.ReviewResponse;

import java.io.IOException;
import java.util.ArrayList;

public interface IYelpMapper {
    ArrayList<ReviewResponse> map(String httpResponse) throws IOException;
    ReviewListResponse mapper(String httpResponse) throws IOException;
}
