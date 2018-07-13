package com.ycr.kernal.log.engine;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuchengren on 2018/7/13.
 */
public class LogEngineFactory {
	private static ILogEngine appLogEngine;
	private static String appModuleName = "app";
	private static Map<String,ILogEngine> logEngineMap = new HashMap<>();

	public static ILogEngine createAppLogEngine(String appModuleName){
		LogEngineFactory.appModuleName = appModuleName;
		return getLogEngine(appModuleName);
	}

	public static ILogEngine getAppLogEngine(){
		return getLogEngine(appModuleName);
	}

	public static ILogEngine getLogEngine(String moduleName){
		ILogEngine moduleEngine = logEngineMap.get(moduleName);
		if(moduleEngine == null){
			moduleEngine = new LogEngine(moduleName);
			logEngineMap.put(moduleName,moduleEngine);
		}
		return moduleEngine;
	}

}
