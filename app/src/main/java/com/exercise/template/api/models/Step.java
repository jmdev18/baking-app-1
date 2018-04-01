package com.exercise.template.api.models;

import java.io.Serializable;

import lombok.Data;

/**
 * File Created by pandu on 31/03/18.
 */
@Data
public class Step implements Serializable {
    private Integer id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;
}
