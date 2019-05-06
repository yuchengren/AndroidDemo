package com.ycr.kernel.log;


import com.ycr.kernel.log.engine.ILogEngine;
import com.ycr.kernel.log.engine.LogEngineFactory;

/**
 * Created by yuchengren on 2018/7/11.
 */
public class LogHelper{

	public static ILogEngine initAppModule(String appModuleName){
		return LogEngineFactory.createAppLogEngine(appModuleName);
	}

	public static ILogEngine module(String moduleName){
		return LogEngineFactory.getLogEngine(moduleName);
	}

	public static void v(String tag, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().v(tag,msg,args);
	}

	public static void v(String tag, Throwable tr, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().v(tag,tr,msg,args);
	}

	public static void v(String tag, Throwable tr) {
		LogEngineFactory.getAppLogEngine().v(tag,tr);
	}

	public static void v(String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().v(msg,args);
	}

	public static void v(Throwable tr, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().v(tr,msg,args);
	}

	public static void v(Throwable tr) {
		LogEngineFactory.getAppLogEngine().v(tr);
	}

	public static void d(String tag, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().d(tag,msg,args);
	}

	public static void d(String tag, Throwable tr, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().d(tag,tr,msg,args);
	}
	public static void d(String tag, Throwable tr) {
		LogEngineFactory.getAppLogEngine().d(tag,tr);
	}

	public static void d(String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().d(msg,args);
	}

	public static void d(Throwable tr, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().d(tr,msg,args);
	}

	public static void d(Throwable tr) {
		LogEngineFactory.getAppLogEngine().d(tr);
	}
	public static void i(String tag, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().i(tag,msg,args);
	}

	public static void i(String tag, Throwable tr, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().i(tag,tr,msg,args);
	}

	public static void i(String tag, Throwable tr) {
		LogEngineFactory.getAppLogEngine().i(tag,tr);
	}

	public static void i(String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().i(msg,args);
	}

	public static void i(Throwable tr, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().i(tr,msg,args);
	}

	public static void i(Throwable tr) {
		LogEngineFactory.getAppLogEngine().i(tr);
	}

	public static void w(String tag, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().w(tag,msg,args);
	}

	public static void w(String tag, Throwable tr, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().w(tag,tr,msg,args);
	}

	public static void w(String tag, Throwable tr) {
		LogEngineFactory.getAppLogEngine().w(tag,tr);
	}

	public static void w(String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().w(msg,args);
	}

	public static void w(Throwable tr, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().w(tr,msg,args);
	}

	public static void w(Throwable tr) {
		LogEngineFactory.getAppLogEngine().w(tr);
	}

	public static void e(String tag, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().e(tag,msg,args);
	}

	public static void e(String tag, Throwable tr, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().e(tag,tr,msg,args);
	}

	public static void e(String tag, Throwable tr) {
		LogEngineFactory.getAppLogEngine().e(tag,tr);
	}

	public static void e(String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().e(msg,args);
	}

	public static void e(Throwable tr, String msg, Object... args) {
		LogEngineFactory.getAppLogEngine().e(tr,msg,args);
	}

	public static void e(Throwable tr) {
		LogEngineFactory.getAppLogEngine().e(tr);
	}
}
