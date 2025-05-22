package com.example.counter;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

public class NewAppWidget extends AppWidgetProvider {

    private static final String ACTION_INC_250 = "com.example.counter.INC_250";
    private static final String ACTION_DEC_250 = "com.example.counter.DEC_250";
    private static final String ACTION_INC_500 = "com.example.counter.INC_500";
    private static final String ACTION_DEC_500 = "com.example.counter.DEC_500";
    private static final String ACTION_INC_750 = "com.example.counter.INC_750";
    private static final String ACTION_DEC_750 = "com.example.counter.DEC_750";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        SharedPreferences prefs = context.getSharedPreferences("counter_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        boolean changed = false;

        if (ACTION_INC_250.equals(intent.getAction())) {
            int count = prefs.getInt("count250", 0) + 1;
            editor.putInt("count250", count);
            changed = true;
        } else if (ACTION_DEC_250.equals(intent.getAction())) {
            int count = Math.max(0, prefs.getInt("count250", 0) - 1);
            editor.putInt("count250", count);
            changed = true;
        } else if (ACTION_INC_500.equals(intent.getAction())) {
            int count = prefs.getInt("count500", 0) + 1;
            editor.putInt("count500", count);
            changed = true;
        } else if (ACTION_DEC_500.equals(intent.getAction())) {
            int count = Math.max(0, prefs.getInt("count500", 0) - 1);
            editor.putInt("count500", count);
            changed = true;
        } else if (ACTION_INC_750.equals(intent.getAction())) {
            int count = prefs.getInt("count750", 0) + 1;
            editor.putInt("count750", count);
            changed = true;
        } else if (ACTION_DEC_750.equals(intent.getAction())) {
            int count = Math.max(0, prefs.getInt("count750", 0) - 1);
            editor.putInt("count750", count);
            changed = true;
        }

        if (changed) {
            editor.apply();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisWidget = new ComponentName(context, NewAppWidget.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
            onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences prefs = context.getSharedPreferences("counter_prefs", Context.MODE_PRIVATE);
        int count250 = prefs.getInt("count250", 0);
        int count500 = prefs.getInt("count500", 0);
        int count750 = prefs.getInt("count750", 0);
        int dailyGoal = prefs.getInt("dailyGoal", 2000);

        int totalMl = (count250 * 250) + (count500 * 500) + (count750 * 750);
        int remaining = Math.max(0, dailyGoal - totalMl);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        views.setTextViewText(R.id.tvTotalMl, "Vypito celkem " + totalMl + " ml");
        views.setTextViewText(R.id.tvRemainingMl, "Zbývá vypít " + remaining + " ml");

        // Nastavení textu počtů
        views.setTextViewText(R.id.tvCount250, String.valueOf(count250));
        views.setTextViewText(R.id.tvCount500, String.valueOf(count500));
        views.setTextViewText(R.id.tvCount750, String.valueOf(count750));

        // Nastavení PendingIntent pro tlačítka 250 ml
        views.setOnClickPendingIntent(R.id.btnAdd250, getPendingIntent(context, ACTION_INC_250));
        views.setOnClickPendingIntent(R.id.btnRemove250, getPendingIntent(context, ACTION_DEC_250));

        // 500 ml
        views.setOnClickPendingIntent(R.id.btnAdd500, getPendingIntent(context, ACTION_INC_500));
        views.setOnClickPendingIntent(R.id.btnRemove500, getPendingIntent(context, ACTION_DEC_500));

        // 750 ml
        views.setOnClickPendingIntent(R.id.btnAdd750, getPendingIntent(context, ACTION_INC_750));
        views.setOnClickPendingIntent(R.id.btnRemove750, getPendingIntent(context, ACTION_DEC_750));

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static PendingIntent getPendingIntent(Context context, String action) {
        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, action.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    public static void updateAllWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


}