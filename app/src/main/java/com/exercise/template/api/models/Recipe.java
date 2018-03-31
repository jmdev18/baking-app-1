package com.exercise.template.api.models;

import java.util.List;

import lombok.Data;

/**
 * File Created by pandu on 31/03/18.
 */
@Data
public class Recipe {
    private String id;
    private String name;
    private Integer servings;
    private String image;
    private List<Ingredient> ingredients;
    private List<Step> steps;
}
