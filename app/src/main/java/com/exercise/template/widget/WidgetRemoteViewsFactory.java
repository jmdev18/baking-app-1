package com.exercise.template.widget;

import android.content.Context;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.exercise.template.Constants;
import com.exercise.template.R;
import com.exercise.template.db.RecipeContract;

/**
 * File Created by pandu on 03/04/18.
 */
public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private Context context;
    private Cursor cursor;

    public WidgetRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if(cursor != null) cursor.close();

        long l = Binder.clearCallingIdentity();
    }

    @Override
    public void onDestroy() {
        if(cursor != null) cursor.close();
    }

    @Override
    public int getCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if(cursor == null || !cursor.moveToPosition(i)){
            return null;
        }

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.list_item_ingredient);
        rv.setTextViewText(R.id.tv_title, cursor.getString(RecipeContract.COL_NUM_NAME));

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
