package com.exercise.template.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * File Created by pandu on 03/04/18.
 */
public class RecipeWidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetRemoteViewsFactory(this.getApplicationContext());
    }
}
