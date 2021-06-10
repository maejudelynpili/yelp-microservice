package com.assessment.yelp.infrastructure.service;

import com.assessment.yelp.abstraction.IGoogleVisionService;
import com.assessment.yelp.config.ApiKeysConfig;
import com.assessment.yelp.enums.Constants;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.FaceAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class for retrieving emotion data
 */
@Service
@RequiredArgsConstructor
public class GoogleVisionService implements IGoogleVisionService {

    /**
     * Retrieves the emotion data from Google Vision API based from the URL
     * @param imageUrl String Image Url
     * @return Map<String, String> Emotion data
     * @throws IOException
     */
    @Override
    public Map<String, String> getEmotionData(String imageUrl) throws IOException {
        Map<String, String> emotionData = new HashMap<>();
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ImageSource imgSource = ImageSource.newBuilder().setImageUri(imageUrl).build();
        Image img = Image.newBuilder().setSource(imgSource).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.FACE_DETECTION).build();

        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);


        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    emotionData.put(Constants.KEY_ERROR, res.getError().getMessage());
                    return emotionData;
                }

                for (FaceAnnotation annotation : res.getFaceAnnotationsList()) {
                        emotionData.put(Constants.KEY_EMOTION_SORROW, annotation.getSorrowLikelihood().name());
                        emotionData.put(Constants.KEY_EMOTION_JOY, annotation.getJoyLikelihood().name());
                }
            }
        }

        return emotionData;
    }

}

