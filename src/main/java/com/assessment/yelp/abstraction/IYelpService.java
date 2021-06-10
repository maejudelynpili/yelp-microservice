package com.assessment.yelp.abstraction;

import com.assessment.yelp.model.ReviewResponse;

import java.io.IOException;
import java.util.ArrayList;

public interface IYelpService {

    ArrayList<ReviewResponse> getReviews(String searchTerm) throws IOException;
}
