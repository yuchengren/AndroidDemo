package com.ycr.kernal.log;


import com.ycr.kernal.log.engine.ILogEngine;
import com.ycr.kernal.log.engine.LogEngineFactory;

/**
 * Created by yuchengren on 2018/7/11.
 */
public class LogHelper {

	public static ILogEngine initAppModule(String appModuleName){
		return LogEngineFactory.createAppLogEngine(appModuleName);
	}

	public static ILogEngine module(String moduleName){
		return LogEngineFactory.getLogEngine(moduleName);
	}

	public static void v(String tag, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().v(tag, msg, args);
	}

	public static void v(String tag, String msg, Throwable tr, Object... args) {
		LogEngineFactory.getAppLogEngine().v(tag, msg,tr, args);
	}

	public static void d(String tag, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().d(tag, msg, args);
	}

	public static void d(String tag, String msg, Throwable tr, Object... args) {
		LogEngineFactory.getAppLogEngine().d(tag, msg,tr, args);
	}

	public static void i(String tag, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().i(tag, msg, args);
	}

	public static void i(String tag, String msg, Throwable tr, Object... args) {
		LogEngineFactory.getAppLogEngine().d(tag, msg,tr, args);
	}

	public static void w(String tag, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().w(tag, msg, args);
	}

	public static void w(String tag, String msg, Throwable tr, Object... args) {
		LogEngineFactory.getAppLogEngine().w(tag, msg,tr, args);
	}

	public static void e(String tag, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().e(tag, msg, args);
	}

	public static void e(String tag, String msg, Throwable tr, Object... args) {
		LogEngineFactory.getAppLogEngine().e(tag, msg, tr,args);
	}
}
