package com.ycr.kernel.union.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuchengren on 2017/12/19.
 */

public class SharePrefsHelper {

	private static final String TAG = SharePrefsHelper.class.getName();

	private  static Map<String,SharedPreferences> sharedPreferencesMap;

	private static final String DEFAULT_SHARE_PRES_FILE_NAME = "config";

	private static SharePrefsHelper instance;

	private static Context context;

	private SharePrefsHelper(){

	}

	public static SharePrefsHelper getInstance(){
		if(instance == null){
			synchronized (SharePrefsHelper.class){
				if(instance == null){
					instance = new SharePrefsHelper();
				}
			}
		}
		return instance;
	}

	public void init(Context context){
		this.context = context;
		this.sharedPreferencesMap = new HashMap<>();
	}


	public static SharedPreferences getSharePrefs(){
		return getSharePrefs(DEFAULT_SHARE_PRES_FILE_NAME);
	}

	public static SharedPreferences getSharePrefs(String name){
		SharedPreferences sharedPreferences = sharedPreferencesMap.get(name);
		if(sharedPreferences == null){
			sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
			sharedPreferencesMap.put(name,sharedPreferences);
		}
		return sharedPreferences;
	}

	public static int getInt(String key){
		return getSharePrefs().getInt(key,0);
	}
	public static int getInt(String key,int defValue){
		return getSharePrefs().getInt(key, defValue);
	}
	public static long getLong(String key){
		return getSharePrefs().getLong(key,0);
	}
	public static long getLong(String key,long defValue){
		return getSharePrefs().getLong(key, defValue);
	}
	public static float getFloat(String key){
		return getSharePrefs().getFloat(key,0);
	}
	public static float getFloat(String key,float defValue){
		return getSharePrefs().getFloat(key, defValue);
	}
	public static String getString(String key){
		return getSharePrefs().getString(key,"");
	}
	public static String getString(String key,String defValue){
		return getSharePrefs().getString(key, defValue);
	}
	public static boolean getBoolean(String key){
		return getSharePrefs().getBoolean(key,false);
	}
	public static boolean getBoolean(String key,boolean defValue){
		return getSharePrefs().getBoolean(key, defValue);
	}

	public static int getInt(String name,String key){
		return getSharePrefs(name).getInt(key,0);
	}
	public static int getInt(String name,String key,int defValue){
		return getSharePrefs(name).getInt(key, defValue);
	}
	public static long getLong(String name,String key){
		return getSharePrefs(name).getLong(key,0);
	}
	public static long getLong(String name,String key,long defValue){
		return getSharePrefs(name).getLong(key, defValue);
	}
	public static float getFloat(String name,String key){
		return getSharePrefs(name).getFloat(key,0);
	}
	public static float getFloat(String name,String key,float defValue){
		return getSharePrefs(name).getFloat(key, defValue);
	}
	public static String getString(String name,String key,String defValue){
		return getSharePrefs(name).getString(key, defValue);
	}
	public static boolean getBoolean(String name,String key){
		return getSharePrefs(name).getBoolean(key,false);
	}
	public static boolean getBoolean(String name,String key,boolean defValue){
		return getSharePrefs(name).getBoolean(key, defValue);
	}

	public static <T> void put(String key,T value){
		put(DEFAULT_SHARE_PRES_FILE_NAME,key,value);
	}

	public static <T> void put(String name ,String key,T value){
		SharedPreferences.Editor edit = getInstance().getSharePrefs(name).edit();
		if(value instanceof Integer){
			edit.putInt(key,(Integer) value);
		}else if(value instanceof Long){
			edit.putLong(key,(Long) value);
		}else if(value instanceof Float){
			edit.putFloat(key,(Float) value);
		}else if(value instanceof Boolean){
			edit.putBoolean(key,(Boolean) value);
		}else if(value instanceof String){
			edit.putString(key,(String)value);
		}else {
			edit.putString(key,JsonHelper.toJson(value));
		}
		edit.commit();
	}
}
