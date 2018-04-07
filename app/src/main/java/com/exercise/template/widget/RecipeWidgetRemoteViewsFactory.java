package com.exercise.template.widget;

import android.content.Context;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.exercise.template.Constants;
import com.exercise.template.R;
import com.exercise.template.api.models.Ingredient;
import com.exercise.template.db.RecipeContract;
import com.exercise.template.db.RecipeProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * File Created by pandu on 03/04/18.
 */
public class RecipeWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private Context context;
    private Cursor cursor;
    private List<Ingredient> dtIngredients;
    private Gson gson;

    public RecipeWidgetRemoteViewsFactory(Context context) {
        this.context = context;
        gson = new Gson();
        dtIngredients = new ArrayList<>();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if(cursor != null) cursor.close();

        long token = Binder.clearCallingIdentity();
        cursor = context.getContentResolver()
                .query(RecipeProvider.Recipes.DESIRED_CONTENT_URI(),
                        RecipeContract.PROJECTION,
                        null,
                        null,
                        null
                );

        if (cursor != null && cursor.moveToFirst()) {
            Type ingredientType = new TypeToken<List<Ingredient>>() {
            }.getType();
            dtIngredients = new ArrayList<>();
            dtIngredients = gson.fromJson(cursor.getString(RecipeContract.COL_NUM_INGREDIENTS), ingredientType);
            Binder.restoreCallingIdentity(token);
        }
    }

    @Override
    public void onDestroy() {
        if(cursor != null) cursor.close();
    }

    @Override
    public int getCount() {
        return dtIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Ingredient ingredient = dtIngredients.get(i);

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
        rv.setTextViewText(R.id.tv_title_widget,
                context.getString(R.string.info_ingredient,
                        ingredient.getIngredient(), ingredient.getQuantity(), ingredient.getMeasure()));

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
