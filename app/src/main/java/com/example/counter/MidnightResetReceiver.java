package com.example.counter;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.ComponentName;

public class MidnightResetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Vynulování hodnot
        SharedPreferences prefs = context.getSharedPreferences("counter_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("count250", 0);
        editor.putInt("count500", 0);
        editor.putInt("count750", 0);
        editor.apply();

        // Aktualizace widgetu
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName widget = new ComponentName(context, NewAppWidget.class);
        int[] ids = appWidgetManager.getAppWidgetIds(widget);
        if (ids != null && ids.length > 0) {
            NewAppWidget.updateAllWidgets(context, appWidgetManager, ids);
        }
    }
}
