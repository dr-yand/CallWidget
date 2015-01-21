package call.widget3.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Utils {
	public static String getNumber(Context context){
		return get(context, "number");
	}
	
	public static void saveNumber(Context context, String number){
		save(context, "number", number);
	}
	
	public static String getName(Context context){
		return get(context, "name");
	}
	
	public static void saveName(Context context, String name){
		save(context, "name", name);
	}
	
	private static String get(Context context, String key){
		String result = "";
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		result = preferences.getString(key, "000000");
		return result;
	}
	
	private static void save(Context context, String key, String value){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
}
