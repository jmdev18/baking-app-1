package com.exercise.template.db;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * File Created by pandu on 04/04/18.
 */
@Database(version = RecipeDb.VERSION)
public final class RecipeDb {

    public static final int VERSION = 1;

    @Table(RecipeContract.class)
    public static final String RECIPES = "RECIPES";
}
