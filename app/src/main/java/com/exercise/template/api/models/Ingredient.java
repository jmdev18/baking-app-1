package com.exercise.template.api.models;

import java.io.Serializable;

import lombok.Data;

/**
 * File Created by pandu on 31/03/18.
 */
@Data
public class Ingredient implements Serializable {
    private Double quantity;
    private String measure;
    private String ingredient;
}
