package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.UI.RecipeDetailActivity;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientsWidget extends AppWidgetProvider {

    private static ArrayList<String> mIngredientsList = new ArrayList<>();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews widgets = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredients_widget);
        Intent recipeIntent = new Intent(context, RecipeDetailActivity.class);
        PendingIntent recipePendingIntent = PendingIntent.getActivity(context,0,recipeIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        widgets.setOnClickPendingIntent(R.id.lv_widget,recipePendingIntent);


        Intent widgetServiceIntent = new Intent(context,RecipeIngredientsWidgetService.class);
        widgets.setRemoteAdapter(R.id.lv_widget, widgetServiceIntent);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, widgets);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(UpdateWidgetService.ACTION_UPDATE_WIDGET)) {
            mIngredientsList=intent.getStringArrayListExtra(UpdateWidgetService.KEY_WIDGET_INGREDIENTS_LIST);
            
        }
        super.onReceive(context, intent);
    }
}

