package call.widget3;
 
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import call.widget3.utils.Utils;

public class MainActivity extends Activity implements OnClickListener {

	static final int PICK_CONTACT_REQUEST = 1;  // The request code
	
	private TextView number, name;
	private ImageView photo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		Window window = this.getWindow(); 
		window.addFlags(LayoutParams.FLAG_DISMISS_KEYGUARD); 
		window.addFlags(LayoutParams.FLAG_SHOW_WHEN_LOCKED); 
		window.addFlags(LayoutParams.FLAG_TURN_SCREEN_ON);
		
		setContentView(R.layout.main_activity);
		
		((Button)findViewById(R.id.button1)).setOnClickListener(this);
		
		initView();
		
		updateWidget();
	}
	
	
	
	@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
		super.onPostResume();
		
		updateWidget();
	}



	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		
		updateWidget();
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		updateWidget();
	}



	private void updateWidget(){
		Intent intent = new Intent(this,MyWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = {R.xml.widget_info};
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);
        
        
        RemoteViews view = new RemoteViews(getPackageName(), R.layout.widget_layout);
        view.setTextViewText(R.id.textView1, Utils.getName(getApplicationContext()));
        view.setTextViewText(R.id.textView2, Utils.getNumber(getApplicationContext()));
        
        view.setOnClickPendingIntent(R.id.imageButton1, MyWidgetProvider.getPendingSelfIntent(getApplicationContext(), MyWidgetProvider.CALL_CLICKED));
        view.setOnClickPendingIntent(R.id.imageButton2, MyWidgetProvider.getPendingSelfIntent(getApplicationContext(), MyWidgetProvider.SETTINGS_CLICKED));

        ComponentName thisWidget = new ComponentName(this, MyWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(thisWidget, view);
	}
	
	private void initView(){
		name = (TextView)findViewById(R.id.textView1);
		number = (TextView)findViewById(R.id.textView2);
		photo = (ImageView)findViewById(R.id.imageView1);
		
		name.setText(Utils.getName(getApplicationContext()));
		number.setText(Utils.getNumber(getApplicationContext()));
	}

	private void pickContact() {
	    Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
	    pickContactIntent.setType(Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
	    startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == PICK_CONTACT_REQUEST) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	// Get the URI that points to the selected contact
	            Uri contactUri = data.getData();
	            // We only need the NUMBER column, because there will be only one row in the result
	            String[] projection = {Phone.NUMBER, Phone.DISPLAY_NAME};

	            // Perform the query on the contact to get the NUMBER column
	            // We don't need a selection or sort order (there's only one result for the given URI)
	            // CAUTION: The query() method should be called from a separate thread to avoid blocking
	            // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
	            // Consider using CursorLoader to perform the query.
	            Cursor cursor = getContentResolver()
	                    .query(contactUri, projection, null, null, null);
	            cursor.moveToFirst();

	            // Retrieve the phone number from the NUMBER column
	            int numberColumn = cursor.getColumnIndex(Phone.NUMBER);
	            String number = cursor.getString(numberColumn);
	            int nameColumn = cursor.getColumnIndex(Phone.DISPLAY_NAME);
	            String name = cursor.getString(nameColumn);
	            
	            this.number.setText(number);
	            this.name.setText(name);
	            
	            Utils.saveNumber(getApplicationContext(), number);
	            Utils.saveName(getApplicationContext(), name);
	            
	            /*Intent intent = new Intent(this,MyWidgetProvider.class);
	            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
	            // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
	            // since it seems the onUpdate() is only fired on that:
	            int[] ids = {R.xml.widget_info};
	            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
	            sendBroadcast(intent);*/
	            updateWidget();
	        }
	    }
	}

	@Override
	public void onClick(View v) {
		pickContact();
	}
}
