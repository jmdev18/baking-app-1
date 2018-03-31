package com.exercise.template.api;

import com.exercise.template.api.models.Ingredient;
import com.exercise.template.api.models.Recipe;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * File Created by pandu on 31/03/18.
 */
public interface AppApi {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Observable<List<Recipe>> getRecipes();
}
