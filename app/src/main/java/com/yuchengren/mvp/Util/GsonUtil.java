package com.yuchengren.mvp.Util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by yuchengren on 2016/12/26.
 */

public class GsonUtil {

	private static Gson mGson = null;
	private static JsonParser mGsonParser = null;
	private static final String TAG = GsonUtil.class.getSimpleName();

	public static Gson getGson(){
		if(mGson == null ){
			synchronized (GsonUtil.class){
				if(mGson == null){
					mGson = new Gson();
				}
			}
		}
		return mGson;
	}
	public static JsonParser getGsonParser(){
		if(mGsonParser == null ){
			synchronized (GsonUtil.class){
				if(mGsonParser == null){
					mGsonParser = new JsonParser();
				}
			}
		}
		return mGsonParser;
	}


	public static String formatObjectToJson(Object object){
		return getGson().toJson(object);
	}

	public static <T> T parseJsonToBean(String json,Class<T> tClass){
		T t = null;
		try{
			t =  getGson().fromJson(json,tClass);
		}catch (Exception e){
			e.printStackTrace();
		}
		return t;
	}

	public static <T> List<T> parseJsonToList(String json,Class<T> tClass){
		List<T> tList = new ArrayList<>();
		try{
//			JsonElement jsonElementSrc = getGsonParser().parse(json);
//			if(jsonElementSrc.isJsonArray()){
//				JsonArray jsonArray = jsonElementSrc.getAsJsonArray();
//				for (JsonElement jsonElement : jsonArray) {
//					tList.add(getGson().fromJson(jsonElement, tClass));
//				}
//			}else{
//				tList.add(getGson().fromJson(jsonElementSrc, tClass));
//			}
// 		T[] tArray = getGson().fromJson(json, tClass);
//		return Arrays.asList(tArray);
			tList = getGson().fromJson(json,new TypeToken<List<T>>(){}.getType());

		}catch(Exception e){
			e.printStackTrace();
			Log.e(TAG,e.toString());
		}
		return tList;
	}

	public static <T> List<Map<String,T>> parseJsonToMapList(String json){
		return getGson().fromJson(json,new TypeToken<List<Map<String,T>>>(){}.getType());
	}

	public static <T> Map<String,T> parseJsonToMap(String json){
		return getGson().fromJson(json,new TypeToken<Map<String,T>>(){}.getType());
	}



}
