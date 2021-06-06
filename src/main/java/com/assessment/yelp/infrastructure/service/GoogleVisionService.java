package com.assessment.yelp.infrastructure.service;

import com.assessment.yelp.abstraction.IGoogleVisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleVisionService implements IGoogleVisionService {

    @Autowired
    private CloudVisionTemplate cloudVisionTemplate;

    @Override
    public String getEmotionData(String imageUrl) {


        return null;
    }
}
