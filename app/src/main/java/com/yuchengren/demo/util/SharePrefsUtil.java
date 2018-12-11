package com.yuchengren.demo.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yuchengren on 2017/12/19.
 */

public class SharePrefsUtil {

	private static final String TAG = SharePrefsUtil.class.getName();

	private static final String DEFAULT_SHARE_PRES_FILE_NAME = "config";

	private static SharePrefsUtil instance;

	private Context context;

	private SharedPreferences sp;

	private SharePrefsUtil(){

	}

	public static SharePrefsUtil getInstance(){
		if(instance == null){
			synchronized (SharePrefsUtil.class){
				if(instance == null){
					instance = new SharePrefsUtil();
				}
			}
		}
		return instance;
	}

	public void init(Context context){
		this.context = context;
		this.sp = getSharePrefs(context);
	}

	public SharedPreferences getSharePrefs(Context context){
		SharedPreferences sp = context.getSharedPreferences(DEFAULT_SHARE_PRES_FILE_NAME, Context.MODE_PRIVATE);
		return sp;
	}


	public int getInt(String key){
		return sp.getInt(key,0);
	}
	public int getInt(String key,int defValue){
		return sp.getInt(key, defValue);
	}
	public long getLong(String key){
		return sp.getLong(key,0);
	}
	public long getLong(String key,long defValue){
		return sp.getLong(key, defValue);
	}
	public float getFloat(String key){
		return sp.getFloat(key,0);
	}
	public float getFloat(String key,float defValue){
		return sp.getFloat(key, defValue);
	}
	public String getString(String key){
		return sp.getString(key,"");
	}
	public String getString(String key,String defValue){
		return sp.getString(key, defValue);
	}
	public boolean getBoolean(String key){
		return sp.getBoolean(key,false);
	}
	public boolean getBoolean(String key,boolean defValue){
		return sp.getBoolean(key, defValue);
	}


	public void putInt(String key,int value){
		put(key,value,ValueType.INT);
	}
	public void putLong(String key,long value){
		put(key,value,ValueType.LONG);
	}
	public void putFloat(String key,float value){
		put(key,value,ValueType.FLOAT);
	}
	public void putString(String key,String value){
		put(key,value,ValueType.STRING);
	}
	public void putBoolean(String key,boolean value){
		put(key,value,ValueType.BOOLEAN);
	}

	private void put(String key,Object value,ValueType valueType){
		SharedPreferences.Editor edit = sp.edit();
		switch (valueType){
			case INT:
				edit.putInt(key,(Integer) value);
				break;
			case LONG:
				edit.putLong(key,(Long) value);
				break;
			case FLOAT:
				edit.putFloat(key,(Float) value);
				break;
			case STRING:
				edit.putString(key,(String)value);
				break;
			case BOOLEAN:
				edit.putBoolean(key,(Boolean) value);
				break;
			default:
				break;
		}
		edit.commit();
	}

	public enum ValueType{
		INT
		,LONG
		,FLOAT
		,STRING
		,BOOLEAN
	}
}
