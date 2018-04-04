package com.exercise.template.db;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * File Created by pandu on 04/04/18.
 */
@ContentProvider(authority = RecipeProvider.AUTHORITY, database = RecipeDb.class)
public final class RecipeProvider {

    public static final String AUTHORITY = "com.exercise.template.db.RecipeProvider";

    @TableEndpoint(table = RecipeDb.RECIPES)
    public static class Recipes {

        @ContentUri(
                path = "recipes",
                    type = "vnd.android.cursor.dir/recipes",
                defaultSort = RecipeContract.COLUMN_NAME + " ASC"
        )
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/recipes");
    }
}
