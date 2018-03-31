package com.exercise.template.api.models;

import lombok.Data;

/**
 * File Created by pandu on 31/03/18.
 */
@Data
public class Step {
    private Integer id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;
}
