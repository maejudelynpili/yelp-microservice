package com.assessment.yelp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewResponse {
    private String id;
    private String url;
    private String text;
    private Integer rating;
    private String createdOn;
    private UserModel user;

}
