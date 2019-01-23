package com.ycr.module.base.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yuchengren on 2016/12/26.
 */

public class GsonUtil {

	private static Gson mGson = null;
	private static JsonParser mGsonParser = null;
	private static final String TAG = GsonUtil.class.getSimpleName();

	public static Gson getGson() {
		if (mGson == null) {
			synchronized (GsonUtil.class) {
				if (mGson == null) {
					mGson = new GsonBuilder()
							.setDateFormat("yyyy-MM-dd")
							.setDateFormat("yyyy-MM-dd HH:mm:ss")
							.serializeNulls()//序列化null
							.registerTypeAdapter(java.util.Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG)
							.registerTypeAdapter(java.util.Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG)
                            .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                            .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                            .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                            .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
							.create();
				}
			}
		}
		return mGson;
	}

	public static JsonParser getGsonParser() {
		if (mGsonParser == null) {
			synchronized (GsonUtil.class) {
				if (mGsonParser == null) {
					mGsonParser = new JsonParser();
				}
			}
		}
		return mGsonParser;
	}


	public static String formatObjectToJson(Object object) {
		return getGson().toJson(object);
	}

	public static <T> T parseJsonToBean(String json, Class<T> tClass) {
		return getGson().fromJson(json, tClass);
	}

	public static <T> List<T> parseJsonToList(String json, Class<T> tClass) {
		List<T> tList = new ArrayList<>();
		JsonElement jsonElementSrc = getGsonParser().parse(json);
		if (jsonElementSrc.isJsonArray()) {
			JsonArray jsonArray = jsonElementSrc.getAsJsonArray();
			for (JsonElement jsonElement : jsonArray) {
				tList.add(getGson().fromJson(jsonElement, tClass));
			}
		} else if (jsonElementSrc.isJsonObject()) {
			tList.add(getGson().fromJson(jsonElementSrc, tClass));
		} else if (jsonElementSrc.isJsonNull()) {
			Log.e(TAG, "json is null!");
		} else if (jsonElementSrc.isJsonPrimitive()) {
			JsonPrimitive asJsonPrimitive = jsonElementSrc.getAsJsonPrimitive();
			Log.e(TAG, "json is jsonPrimitive,json=" + asJsonPrimitive.toString());
		}
//			tList = getGson().fromJson(json,new TypeToken<List<T>>(){}.getType());
		return tList;
	}

//	public static <T> List<T> parseJsonToList(String json,Class<T[]> tClass){
//		List<T> tList = new ArrayList<>();
//		try{
// 		    T[] tArray = getGson().fromJson(json, tClass);
//			tList =  Arrays.asList(tArray);
//		}catch(Exception e){
//			e.printStackTrace();
//			Log.e(TAG,e.toString());
//		}
//		return tList;
//	}

	public static <T> List<Map<String, T>> parseJsonToMapList(String json) {
		return getGson().fromJson(json, new TypeToken<List<Map<String, T>>>() {
		}.getType());
	}

	public static <T> Map<String, T> parseJsonToMap(String json) {
		return getGson().fromJson(json, new TypeToken<Map<String, T>>() {
		}.getType());
	}

	public static class DateDeserializer implements JsonDeserializer<Date> {

		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = null;
			long time;
			if ((json.toString().contains("-") && json.getAsJsonPrimitive().isString()) || TextUtils.isEmpty(json.getAsJsonPrimitive().getAsString().trim())) {
				try {
					String jsonTrim = json.toString().trim();
					if(jsonTrim.contains(" ")){
						date = sdf2.parse(json.getAsJsonPrimitive().getAsString());
					}else{
						date = sdf.parse(json.getAsJsonPrimitive().getAsString());
					}
				} catch (ParseException e) {
					Log.e(TAG, e.toString());
					date = new Date();
				}
				time = date.getTime();
			} else {
				time = json.getAsJsonPrimitive().getAsLong();
			}
			return new java.util.Date(time);
		}
	}

	public static class DateSerializer implements JsonSerializer<Date> {
		public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.getTime());
		}
	}

    public static class IntegerDefault0Adapter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {
        @Override
        public Integer deserialize(JsonElement json, Type typeOfT,
                                   JsonDeserializationContext context) throws JsonParseException {
            try {
                if (json.getAsString().equals("")){
                    return 0;
                }
            } catch (Exception ignore){
            }
            try {
                return json.getAsInt();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }

    public static class DoubleDefault0Adapter implements JsonSerializer<Double>, JsonDeserializer<Double> {
        @Override
        public Double deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {
            try {
                if (json.getAsString().equals("")){
                    return 0D;
                }
            } catch (Exception ignore){
            }
            try {
                return json.getAsDouble();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }

//	public static class DateMonthSerializer implements JsonSerializer<Date> {
//		public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
//			return new JsonPrimitive(src.getTime());
//		}
//	}
//
//	public static class DateMonthDeserializer implements JsonDeserializer<Date> {
//		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			Date date;
//			try {
//				date = sdf.parse(json.getAsJsonPrimitive().getAsString());
//			} catch (ParseException e) {
//				Log.e(TAG,e.toString());
//			}
//			return new java.util.Date(json.getAsJsonPrimitive().getAsString());
//		}
//	}
}
