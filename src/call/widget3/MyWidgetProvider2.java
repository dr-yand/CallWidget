package call.widget3;

import java.util.Random;
 


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyWidgetProvider2 extends AppWidgetProvider {

  private static final String ACTION_CLICK = "ACTION_CLICK";

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager,
      int[] appWidgetIds) {

    ComponentName thisWidget = new ComponentName(context,
        MyWidgetProvider2.class);
    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
    for (int widgetId : allWidgetIds) {
      int number = (new Random().nextInt(100));

      RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
          R.layout.widget_layout);

      remoteViews.setTextViewText(R.id.update, String.valueOf(number));

      Intent intent = new Intent(context, MyWidgetProvider2.class);

      intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
      intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

      PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
          0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//      remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
      
      remoteViews.setOnClickPendingIntent(R.id.imageButton1, pendingIntent);
      
      String uri = "tel:" + "0009999000" ;
      Intent intent3 = new Intent(Intent.ACTION_CALL);
      intent3.setData(Uri.parse(uri));
      intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//      context.startActivity(intent3);
      
      Intent settingsIntent = new Intent(context, MainActivity.class);
      settingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      PendingIntent settingsPendingIntent = PendingIntent.getBroadcast(context,
              0, settingsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
      remoteViews.setOnClickPendingIntent(R.id.imageButton2, settingsPendingIntent);
      
      appWidgetManager.updateAppWidget(widgetId, remoteViews);
    }
  }
  
  @Override
  public void onReceive(Context context, Intent intent) {
      super.onReceive(context, intent);
       
  }
} 