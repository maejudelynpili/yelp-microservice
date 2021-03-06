package com.assessment.yelp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserModel {
    private String id;
    private String image_url;
    private String name;
    private String profile_url;
    private EmotionModel emotion;

}
