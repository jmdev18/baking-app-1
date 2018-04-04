package com.exercise.template.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.exercise.template.R;

import timber.log.Timber;

/**
 * File Created by pandu on 04/04/18.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_main);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
