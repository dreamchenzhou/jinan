package topevery.um.com.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {
	public static final String fileName = "topevry_ssp";
	
	private static SharedPreferences.Editor getEditor(Context context){
		SharedPreferences sp = context.getSharedPreferences(fileName,Context.MODE_WORLD_WRITEABLE);
		return sp.edit();
	}
	
	private static SharedPreferences getSp(Context context){
		SharedPreferences sp = context.getSharedPreferences(fileName,Context.MODE_WORLD_WRITEABLE);
		sp.edit().commit();
		return  sp;
	}
	
	public static void putBoolean(Context context ,String key,boolean value){
		SharedPreferences.Editor editor = getEditor(context);
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public static boolean getBoolean(Context context ,String key){
		SharedPreferences sp = getSp(context);
		return sp.getBoolean(key, false);
	}
	
	public static void putString(Context context ,String key,String value){
		SharedPreferences.Editor editor = getEditor(context);
		editor.putString(key, value);
		editor.commit();
	}
	
	public static String getString(Context context ,String key){
		SharedPreferences sp = getSp(context);
		return sp.getString(key,"");
	}
	
	
	public static void putInt(Context context ,String key,int value){
		SharedPreferences.Editor editor = getEditor(context);
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static int getInt(Context context ,String key){
		SharedPreferences sp = getSp(context);
		return sp.getInt(key,0);
	}
}
