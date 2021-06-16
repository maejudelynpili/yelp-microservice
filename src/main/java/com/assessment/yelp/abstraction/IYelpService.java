package com.assessment.yelp.abstraction;

import com.assessment.yelp.model.ReviewListResponse;

import java.io.IOException;

public interface IYelpService {

    ReviewListResponse getReviews(String searchTerm) throws IOException;
}
