package com.ycr.kernel.log.control;

import android.text.TextUtils;
import android.util.Log;

import com.ycr.kernel.log.constants.LogLevel;
import com.ycr.kernel.log.printer.ConsoleLogPrinter;
import com.ycr.kernel.log.printer.ILogPrinter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by yuchengren on 2018/7/13.
 */
public class LogControl implements ILogControl {
	private static final String TIME_FORMAT_LAST_MILLIS = "yyyy-MM-dd HH:mm:ss.SSS";

	@Override
	public String buildPrintTag(ILogPrinter logPrinter, int level, String preTag, String moduleName, String tag) {
		StringBuilder printTag = new StringBuilder();
		boolean isConsoleLogPrinter= logPrinter instanceof ConsoleLogPrinter;
		if(!isConsoleLogPrinter){
			printTag.append(getCurrentTime() + " ");
		}
		String levelString = getLevelString(level);
		if(!TextUtils.isEmpty(levelString) && !isConsoleLogPrinter){
			printTag.append(levelString + "/");
		}
		if(!TextUtils.isEmpty(preTag)){
			printTag.append(preTag + "-");
		}
		if(!TextUtils.isEmpty(moduleName)){
			printTag.append(moduleName);
		}
		if(!TextUtils.isEmpty(tag)){
			printTag.append("-" + tag);
		}
		if(!isConsoleLogPrinter){
			printTag.append(": ");
		}
		return printTag.toString();
	}

	@Override
	public String buildPrintMessage(String msg, Throwable tr, Object... args) {
		String printMessage ;
		try {
			printMessage =  String.format(msg, args);
		} catch (Throwable throwable) {
			printMessage =  msg;
		}
		if(tr != null){
			printMessage += '\n' + Log.getStackTraceString(tr);
		}
		return printMessage;
	}

	private String getLevelString(int level) {
		String levelString = "";
		switch (level){
			case LogLevel.VERBOSE:
				levelString = "v";
				break;
			case LogLevel.DEBUG:
				levelString = "d";
				break;
			case LogLevel.INFO:
				levelString = "i";
				break;
			case LogLevel.WARN:
				levelString = "w";
				break;
			case LogLevel.ERROR:
				levelString = "e";
				break;
			case LogLevel.ASSERT:
				levelString = "a";
				break;
		}
		return levelString;
	}

	@Override
	public boolean enabled(boolean configEnabled, int enabledLevel, int level, Set<ILogPrinter> logPrinters) {
		boolean isLogPrinterTypesEmpty = logPrinters == null || logPrinters.size() == 0;
		return configEnabled && level >= enabledLevel && !isLogPrinterTypesEmpty;
	}

	private String getCurrentTime(){
		SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT_LAST_MILLIS);
		return format.format(new Date());
	}
}
