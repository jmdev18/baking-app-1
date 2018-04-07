package com.exercise.template.db;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * File Created by pandu on 04/04/18.
 */
@ContentProvider(authority = RecipeProvider.AUTHORITY, database = RecipeDb.class)
public final class RecipeProvider {

    public static final String AUTHORITY = "com.exercise.template.db.RecipeProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    @TableEndpoint(table = RecipeDb.RECIPES)
    public static class Recipes {

        private static Uri buildUri(String... paths) {
            Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
            for (String path : paths) {
                builder.appendPath(path);
            }
            return builder.build();
        }

        @ContentUri(
                path = "recipes",
                type = "vnd.android.cursor.dir/recipes"
        )
        public static final Uri CONTENT_URI = buildUri("recipes");

        @InexactContentUri(
                path = "recipes/id/#",
                name = "recipe_id",
                type = "vnd.android.cursor.item/recipes",
                whereColumn = RecipeContract._ID,
                pathSegment = 2
        )
        public static Uri CONTENT_URI_WITH_ID(String id){
            return buildUri("recipes", "id", id);
        }

        @InexactContentUri(
                path = "recipes/desired/#",
                name = "recipe_desired",
                type = "vnd.android.cursor.item/recipes",
                whereColumn = RecipeContract.COLUMN_DESIRED,
                pathSegment = 2
        )
        public static Uri DESIRED_CONTENT_URI(){
            return buildUri("recipes", "desired", "1");
        }
    }
}
