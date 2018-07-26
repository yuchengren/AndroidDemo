package com.ycr.kernel.log.printer;

import android.content.Context;

import com.ycr.kernel.log.config.ILogFileConfig;
import com.ycr.kernel.log.constants.LogPrinterType;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by yuchengren on 2018/7/16.
 */
public class LogPrinterFactory {

	private static Map<Integer,ILogPrinter> iLogPrinters = new TreeMap<>();

	public static ILogPrinter create(Context context,@LogPrinterType int logPrinterType,ILogFileConfig logFileConfig){
		ILogPrinter logPrinter = iLogPrinters.get(logPrinterType);
		if(logPrinter == null){
			switch (logPrinterType){
				case LogPrinterType.CONSOLE:
					logPrinter = new ConsoleLogPrinter();
					break;
				case LogPrinterType.FILE:
					logPrinter = new FileLogPrinter(context,logFileConfig);
					break;
			}
			iLogPrinters.put(logPrinterType,logPrinter);
		}
		return logPrinter;
	}

}
