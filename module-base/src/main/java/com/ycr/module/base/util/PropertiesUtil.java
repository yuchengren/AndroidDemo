package com.ycr.module.base.util;

import com.ycr.kernel.union.helper.ContextHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by yuchengren on 2017/11/27.
 */

public class PropertiesUtil {
	private static final String TAG = PropertiesUtil.class.getSimpleName();

	public static final String PROPERTIES_FILE_CONFIG = "config.properties";

	public static int getIntProperty(String key){
		int value = 0;
		try {
			String property = getPropertiesObject().getProperty(key);
			value = Integer.parseInt(property);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static double getDoubleProperty(String key){
		double value = 0;
		try {
			String property = getPropertiesObject().getProperty(key);
			value = Double.parseDouble(property);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static Properties getPropertiesObject() throws IOException {
		Properties properties = new Properties();
		InputStream open = ContextHelper.INSTANCE.getContext().getAssets().open(PROPERTIES_FILE_CONFIG);
		properties.load(open);
		return properties;
	}

	public static String getProperty(String key){
		String value = "";
		try {
			value = getPropertiesObject().getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
}
