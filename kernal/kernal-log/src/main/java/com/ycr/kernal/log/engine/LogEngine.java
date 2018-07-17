package com.ycr.kernal.log.engine;

import android.support.annotation.NonNull;

import com.ycr.kernal.log.config.ILogConfig;
import com.ycr.kernal.log.constants.LogLevel;
import com.ycr.kernal.log.control.ILogControl;
import com.ycr.kernal.log.control.LogControl;
import com.ycr.kernal.log.printer.ILogPrinter;
import com.ycr.kernal.log.printer.LogPrinterFactory;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by yuchengren on 2018/7/12.
 */
public class LogEngine implements ILogEngine{

	private String moduleName;
	private ILogConfig logConfig ;
	private ILogControl logControl;
	private Map<Integer,ILogPrinter> iLogPrinters;

	public LogEngine(String moduleName){
		this.moduleName  = moduleName;
		ILogEngine appLogEngine = LogEngineFactory.getAppLogEngine();
		if(appLogEngine != null){
			logConfig = appLogEngine.logConfig();
			logControl = appLogEngine.logControl();
			iLogPrinters = appLogEngine.logPrinters();
		}
	}

	@Override
	public ILogEngine config(@NonNull ILogConfig logConfig) {
		this.logConfig = logConfig;
		initLogPrinter(logConfig.logPrinterTypes());
		if(logControl == null){
			logControl = new LogControl();
		}
		return this;
	}

	private void initLogPrinter(Set<Integer> logPrinterTypes) {
		if(logPrinterTypes == null || logPrinterTypes.size() == 0){
			return;
		}
		iLogPrinters = new TreeMap<>();
		for (Integer logPrinterType : logPrinterTypes) {
			if(logPrinterType == null){
				continue;
			}
			ILogPrinter logPrinter = LogPrinterFactory.create(logConfig.context(),logPrinterType,logConfig.logFileConfig());
			if(logPrinter != null){
				iLogPrinters.put(logPrinterType,logPrinter);
			}
		}
	}

	@Override
	public ILogConfig logConfig() {
		return logConfig;
	}

	@Override
	public ILogControl logControl() {
		return logControl;
	}

	@Override
	public Map<Integer,ILogPrinter> logPrinters() {
		return iLogPrinters;
	}

	private boolean enabled(@LogLevel int level){
		return logControl.enabled(logConfig.enabled(),logConfig.level(),level,logConfig.logPrinterTypes());
	}

	private void print(@LogLevel int level,String tag,String msg,Throwable tr, Object... args){
		Set<Map.Entry<Integer, ILogPrinter>> entrySet = iLogPrinters.entrySet();
		for (Map.Entry<Integer, ILogPrinter> entry : entrySet) {
			String printTag = logControl.buildPrintTag(entry.getKey(),level, logConfig.tagPre(),moduleName, tag);
			String printMessage = logControl.buildPrintMessage(msg, tr, args);
			entry.getValue().print(level,printTag,printMessage);
		}
	}

	private void doPrint(@LogLevel int level,String tag, String msg, Throwable tr, Object... args){
		if(enabled(level)){
			print(level,tag,msg,tr,args);
		}
	}

	@Override
	public void v(String tag, String msg, Object... args) {
		doPrint(LogLevel.VERBOSE,tag, msg, null, args);
	}

	@Override
	public void v(String tag, String msg, Throwable tr, Object... args) {
		doPrint(LogLevel.VERBOSE,tag, msg, tr, args);
	}

	@Override
	public void d(String tag, String msg, Object... args) {
		doPrint(LogLevel.DEBUG,tag, msg, null, args);
	}

	@Override
	public void d(String tag, String msg, Throwable tr, Object... args) {
		doPrint(LogLevel.DEBUG,tag, msg, tr, args);
	}

	@Override
	public void i(String tag, String msg, Object... args) {
		doPrint(LogLevel.INFO,tag, msg, null, args);
	}

	@Override
	public void i(String tag, String msg, Throwable tr, Object... args) {
		doPrint(LogLevel.INFO,tag, msg, tr, args);
	}

	@Override
	public void w(String tag, String msg, Object... args) {
		doPrint(LogLevel.WARN,tag, msg, null, args);
	}

	@Override
	public void w(String tag, String msg, Throwable tr, Object... args) {
		doPrint(LogLevel.WARN,tag, msg, tr, args);
	}

	@Override
	public void e(String tag, String msg, Object... args) {
		doPrint(LogLevel.ERROR,tag, msg, null, args);
	}

	@Override
	public void e(String tag, String msg, Throwable tr, Object... args) {
		doPrint(LogLevel.ERROR,tag, msg, tr, args);
	}
}
