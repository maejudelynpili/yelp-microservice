package com.assessment.yelp.controller;

import com.assessment.yelp.abstraction.IYelpService;
import com.assessment.yelp.model.ReviewRequest;
import com.assessment.yelp.model.ReviewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * API endpoint for dealing with reviews
 */
@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final IYelpService service;

    /**
     * Retrieves review data
     * @param request {@link ReviewRequest} Business Name
     * @return ResponseEntity<{@link ReviewResponse}> Review Response with Emotion Data
     */
    @GetMapping(value = "/business", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ReviewResponse> getReviews (@RequestBody @Valid ReviewRequest request) {

        try {
            ArrayList<ReviewResponse> data = service.getReviews(request.getBusinessName());

            return new ResponseEntity(data, HttpStatus.OK);
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            return new ResponseEntity(errors.toString(), HttpStatus.OK);
        }
    }
}
