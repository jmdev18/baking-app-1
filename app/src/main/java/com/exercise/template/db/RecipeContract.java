package com.exercise.template.db;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * File Created by pandu on 04/04/18.
 */
public interface RecipeContract {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String COLUMN_NAME = "name";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    String COLUMN_SERVINGS = "servings";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String COLUMN_IMAGE = "image";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String COLUMN_INGREDIENTS = "ingredients";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    String COLUMN_INGREDIENTS_SIZE = "ingredients_size";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String COLUMN_STEPS = "steps";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    String COLUMN_STEPS_SIZE = "steps_size";

    String[] PROJECTION = {
        _ID,
        COLUMN_NAME,
        COLUMN_SERVINGS,
        COLUMN_INGREDIENTS,
        COLUMN_STEPS,
        COLUMN_INGREDIENTS_SIZE,
        COLUMN_STEPS_SIZE,
        COLUMN_IMAGE,
    };

    int COL_NUM_ID = 0;
    int COL_NUM_NAME = 1;
    int COL_NUM_SERVINGS = 2;
    int COL_NUM_INGREDIENTS = 3;
    int COL_NUM_STEPS = 4;
    int COL_NUM_INGREDIENTS_SIZE = 5;
    int COL_NUM_STEPS_SIZE = 6;
    int COL_NUM_IMAGE = 7;
}
