package com.zhpew.meandstar.widget

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.zhpew.meandstar.R
import com.zhpew.meandstar.activity.SplashActivity

class AppWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        val N = appWidgetIds.size
        for(i in 0 until N){
            val appWidgetId = appWidgetIds[i]

            // Create an Intent to launch ExampleActivity
            val intent = Intent(context, SplashActivity::class.java)

            val pendingIntent = PendingIntent.getActivity(context, 0, intent, FLAG_IMMUTABLE)

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            val views = RemoteViews(context.getPackageName(), R.layout.app_widget_layout)
//            views.setImage
            views.setOnClickPendingIntent(R.id.widget_container, pendingIntent)

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}