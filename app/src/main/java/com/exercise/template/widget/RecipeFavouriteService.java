package com.exercise.template.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.exercise.template.R;
import com.exercise.template.db.RecipeContract;
import com.exercise.template.db.RecipeProvider;

import timber.log.Timber;

/**
 * File Created by pandu on 07/04/18.
 */
public class RecipeFavouriteService extends IntentService {

    public static final String ACTION_FAVOURITE_RECIPE = "com.exercise.template.action.favourite_recipe";

    public RecipeFavouriteService() {
        super("RecipeFavouriteService");
    }

    public static void startActionFavouriteRecipe(Context context) {
        Intent intent = new Intent(context, RecipeFavouriteService.class);
        intent.setAction(ACTION_FAVOURITE_RECIPE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FAVOURITE_RECIPE.equals(action)) {
                handleActionFavouriteRecipe();
            }
        }
    }

    private void handleActionFavouriteRecipe(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));

        Cursor cursor = getContentResolver()
                .query(RecipeProvider.Recipes.DESIRED_CONTENT_URI(),
                        RecipeContract.PROJECTION,
                        null,
                        null,
                        null
                );

        if(cursor != null && cursor.moveToFirst()) {
            String recipeName = cursor.getString(RecipeContract.COL_NUM_NAME);
            RecipeWidgetProvider.updateRecipeWidget(
                    this,
                    appWidgetManager,
                    appWidgetIds,
                    recipeName
            );

            cursor.close();
        }

    }
}
