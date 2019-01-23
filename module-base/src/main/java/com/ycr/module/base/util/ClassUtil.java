package com.ycr.module.base.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by yuchengren on 2016/9/18.
 */
public class ClassUtil {

	public static <T>T getInstance(Class<T> c){
		T t = null;
		try {
			Constructor<T> constructor = c.getDeclaredConstructor();
			if(!constructor.isAccessible()){
				constructor.setAccessible(true);
			}
			t = constructor.newInstance();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return t;
	}

}
