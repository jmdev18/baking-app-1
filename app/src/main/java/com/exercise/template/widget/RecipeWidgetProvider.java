package com.exercise.template.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.exercise.template.R;

import timber.log.Timber;

/**
 * File Created by pandu on 04/04/18.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String recipe){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_main);

        // Update recipe title
        views.setTextViewText(R.id.tv_welcome, recipe);

        //update list view
        Intent intent = new Intent(context, RecipeWidgetRemoteViewsService.class);
        views.setRemoteAdapter(R.id.list_view_widget, intent);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.list_view_widget);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RecipeFavouriteService.startActionFavouriteRecipe(context);
    }

    public static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager,
                                          int[] appWidgetIds, String recipe) { ;
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe);
        }
    }

//    for (int appWidgetId : appWidgetIds) {
//        RemoteViews mainView = new RemoteViews(context.getPackageName(), R.layout.widget_main);
//
//        Intent intent = new Intent(context, RecipeWidgetRemoteViewsService.class);
//        mainView.setRemoteAdapter(R.id.list_view_widget, intent);
//        appWidgetManager.updateAppWidget(appWidgetId, mainView);
//    }

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        final String action = intent.getAction();
//        if (action != null && action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
//            // refresh all your widgets
//            AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
//            ComponentName component = new ComponentName(context, RecipeWidgetProvider.class);
//            widgetManager.notifyAppWidgetViewDataChanged(widgetManager.getAppWidgetIds(component), R.id.list_view_widget);
//        }
//        super.onReceive(context, intent);
//    }
}
