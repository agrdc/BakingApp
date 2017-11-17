package com.example.android.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by lsitec207.neto on 17/11/17.
 */

class RecipeIngredientsWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return(new RemoteViewsFactory(this.getApplicationContext(),
                intent));
    }
}
