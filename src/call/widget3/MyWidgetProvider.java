package call.widget3;

import java.util.Date;
import java.util.Random;
 







import call.widget3.utils.Utils;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.sax.StartElementListener;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyWidgetProvider extends AppWidgetProvider {

	static final String CALL_CLICKED    = "callButtonClick";
	static final String SETTINGS_CLICKED    = "settingsButtonClick";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews;
        ComponentName watchWidget;

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        watchWidget = new ComponentName(context, MyWidgetProvider.class);

        remoteViews.setOnClickPendingIntent(R.id.imageButton1, getPendingSelfIntent(context, CALL_CLICKED));
        remoteViews.setOnClickPendingIntent(R.id.imageButton2, getPendingSelfIntent(context, SETTINGS_CLICKED));
        
        remoteViews.setTextViewText(R.id.textView1, Utils.getName(context));
        remoteViews.setTextViewText(R.id.textView2, Utils.getNumber(context));
        
        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);

        if (CALL_CLICKED.equals(intent.getAction())) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;
            ComponentName watchWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            watchWidget = new ComponentName(context, MyWidgetProvider.class);

//            Toast.makeText(context, "CALL_CLICKED", Toast.LENGTH_LONG).show();
            
            String uri = "tel:" + Utils.getNumber(context) ;
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse(uri));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(callIntent);

            appWidgetManager.updateAppWidget(watchWidget, remoteViews);
        }
        
        if (SETTINGS_CLICKED.equals(intent.getAction())) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;
            ComponentName watchWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            watchWidget = new ComponentName(context, MyWidgetProvider.class);
            
            Intent settingsIntent = new Intent(context, MainActivity.class);
            settingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(settingsIntent);

//            Toast.makeText(context, "SETTINGS_CLICKED", Toast.LENGTH_LONG).show();

            appWidgetManager.updateAppWidget(watchWidget, remoteViews);
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews;
        ComponentName watchWidget;

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        watchWidget = new ComponentName(context, MyWidgetProvider.class);
        
        remoteViews.setTextViewText(R.id.textView1, Utils.getName(context));
        remoteViews.setTextViewText(R.id.textView2, Utils.getNumber(context));
        
        remoteViews.setOnClickPendingIntent(R.id.imageButton1, getPendingSelfIntent(context, CALL_CLICKED));
        remoteViews.setOnClickPendingIntent(R.id.imageButton2, getPendingSelfIntent(context, SETTINGS_CLICKED));
        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }

    protected static PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, MyWidgetProvider.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
} 