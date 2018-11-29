package com.ycr.kernel.log.engine;

import android.support.annotation.NonNull;

import com.ycr.kernel.log.config.ILogConfig;
import com.ycr.kernel.log.config.LogConfig;
import com.ycr.kernel.log.constants.LogLevel;
import com.ycr.kernel.log.control.ILogControl;
import com.ycr.kernel.log.control.LogControl;
import com.ycr.kernel.log.printer.ILogPrinter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuchengren on 2018/7/12.
 */
public class LogEngine implements ILogEngine{

	private String moduleName;
	private ILogConfig logConfig ;
	private ILogControl logControl;
	private Set<ILogPrinter> logPrinters;

	public LogEngine(String moduleName){
		this.moduleName  = moduleName;
		ILogEngine appLogEngine = LogEngineFactory.getAppLogEngine();
		if(appLogEngine != null){
			logConfig = appLogEngine.logConfig();
			logControl = appLogEngine.logControl();
			logPrinters = appLogEngine.logPrinters();
		}
	}

	@Override
	public ILogEngine config(@NonNull ILogConfig logConfig) {
		this.logConfig = logConfig;
		if(logControl == null){
			logControl = new LogControl();
		}
		return this;
	}

	public ILogEngine setLogPrinters(ILogPrinter... logPrinters) {
		this.logPrinters = new HashSet(Arrays.asList(logPrinters));
		return this;
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
	public Set<ILogPrinter> logPrinters() {
		return logPrinters;
	}

	private boolean enabled(@LogLevel int level){
		return logControl.enabled(logConfig.enabled(),logConfig.level(),level,logPrinters);
	}

	private void print(@LogLevel int level,String tag,String msg,Throwable tr, Object... args){
		for (ILogPrinter logPrinter : logPrinters) {
			if(level >= logPrinter.level()){
				String printTag = logControl.buildPrintTag(logPrinter,level, logConfig.tagPre(),moduleName, tag);
				String printMessage = logControl.buildPrintMessage(msg, tr, args);
				logPrinter.print(level,printTag,printMessage);
			}
		}
	}

	private void doPrint(@LogLevel int level, String tag, Throwable tr, String msg, Object... args){
		if(enabled(level)){
			print(level,tag,msg,tr,args);
		}
	}

	@Override
	public void v(String tag, String msg, Object... args) {
		doPrint(LogLevel.VERBOSE,tag, null, msg, args);
	}

	@Override
	public void v(String tag, Throwable tr, String msg, Object... args) {
		doPrint(LogLevel.VERBOSE,tag, tr, msg, args);
	}

	@Override
	public void v(String tag, Throwable tr) {
		doPrint(LogLevel.VERBOSE,tag, null, null);
	}

	@Override
	public void v(String msg, Object... args) {
		doPrint(LogLevel.VERBOSE,null, null, msg,args);
	}

	@Override
	public void v(Throwable tr, String msg, Object... args) {
		doPrint(LogLevel.VERBOSE,null, tr, msg,args);
	}

	@Override
	public void v(Throwable tr) {
		doPrint(LogLevel.VERBOSE,null, tr, null);
	}

	@Override
	public void d(String tag, String msg, Object... args) {
		doPrint(LogLevel.DEBUG,tag, null, msg, args);
	}

	@Override
	public void d(String tag, Throwable tr, String msg, Object... args) {
		doPrint(LogLevel.DEBUG,tag, tr, msg, args);
	}

	@Override
	public void d(String tag, Throwable tr) {
		doPrint(LogLevel.DEBUG,tag, tr, null);
	}

	@Override
	public void d(String msg, Object... args) {
		doPrint(LogLevel.DEBUG,null, null, msg, args);
	}

	@Override
	public void d(Throwable tr, String msg, Object... args) {
		doPrint(LogLevel.DEBUG,null, tr, msg, args);
	}

	@Override
	public void d(Throwable tr) {
		doPrint(LogLevel.DEBUG,null, tr, null);
	}

	@Override
	public void i(String tag, String msg, Object... args) {
		doPrint(LogLevel.INFO,tag, null, msg, args);
	}

	@Override
	public void i(String tag, Throwable tr, String msg, Object... args) {
		doPrint(LogLevel.INFO,tag, tr, msg, args);
	}

	@Override
	public void i(String tag, Throwable tr) {
		doPrint(LogLevel.INFO,tag, tr,null);
	}

	@Override
	public void i(String msg, Object... args) {
		doPrint(LogLevel.INFO,null, null, msg, args);
	}

	@Override
	public void i(Throwable tr, String msg, Object... args) {
		doPrint(LogLevel.INFO,null, tr, msg, args);
	}

	@Override
	public void i(Throwable tr) {
		doPrint(LogLevel.INFO,null, tr, null);
	}

	@Override
	public void w(String tag, String msg, Object... args) {
		doPrint(LogLevel.WARN,tag, null, msg, args);
	}

	@Override
	public void w(String tag, Throwable tr, String msg, Object... args) {
		doPrint(LogLevel.WARN,tag, tr, msg, args);
	}

	@Override
	public void w(String tag, Throwable tr) {
		doPrint(LogLevel.WARN,tag, tr, null);
	}

	@Override
	public void w(String msg, Object... args) {
		doPrint(LogLevel.WARN,null, null, msg, args);
	}

	@Override
	public void w(Throwable tr, String msg, Object... args) {
		doPrint(LogLevel.WARN,null, tr, msg, args);
	}

	@Override
	public void w(Throwable tr) {
		doPrint(LogLevel.WARN,null, tr, null);
	}

	@Override
	public void e(String tag, String msg, Object... args) {
		doPrint(LogLevel.ERROR,tag, null, msg, args);
	}

	@Override
	public void e(String tag, Throwable tr, String msg, Object... args) {
		doPrint(LogLevel.ERROR,tag, tr, msg, args);
	}

	@Override
	public void e(String tag, Throwable tr) {
		doPrint(LogLevel.ERROR,tag, tr, null);
	}

	@Override
	public void e(String msg, Object... args) {
		doPrint(LogLevel.ERROR,null, null, msg, args);
	}

	@Override
	public void e(Throwable tr, String msg, Object... args) {
		doPrint(LogLevel.ERROR,null, tr, msg, args);
	}

	@Override
	public void e(Throwable tr) {
		doPrint(LogLevel.ERROR,null, tr, null);
	}
}
