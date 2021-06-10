package com.assessment.yelp.abstraction;

import java.io.IOException;
import java.util.Map;

public interface IGoogleVisionService {
    Map<String, String> getEmotionData(String imageUrl) throws IOException;

}
