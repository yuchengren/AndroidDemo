package com.ycr.kernal.log.config;

import android.content.Context;

import com.ycr.kernal.log.constants.LogLevel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuchengren on 2018/7/12.
 */
public class LogConfig implements ILogConfig{

	private Context context;
	private String tagPre;
	private boolean enabled;
	private int level;
	private Set<Integer> logPrinterTypes;
	private ILogFileConfig logFileConfig;

	public LogConfig(Context context){
		this.context = context;
	}

	public static LogConfig create(Context context){
		return new LogConfig(context);
	}

	public LogConfig setContext(Context context){
		this.context = context;
		return this;
	}

	public LogConfig setLogFileConfig(ILogFileConfig logFileConfig) {
		this.logFileConfig = logFileConfig;
		return this;
	}

	public LogConfig setTagPre(String tagPre) {
		this.tagPre = tagPre;
		return this;
	}

	public LogConfig setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public LogConfig setLevel(@LogLevel int level) {
		this.level = level;
		return this;
	}

	public LogConfig setLogPrinterTypes(Integer... logPrinterTypes) {
		if(logPrinterTypes != null && logPrinterTypes.length != 0){
			this.logPrinterTypes = new HashSet<>(Arrays.asList(logPrinterTypes));
		}
		return this;
	}

	@Override
	public Context context() {
		return context;
	}

	@Override
	public String tagPre() {
		return tagPre;
	}

	@Override
	public int level() {
		return level;
	}

	@Override
	public boolean enabled() {
		return enabled;
	}

	@Override
	public Set<Integer> logPrinterTypes() {
		return logPrinterTypes;
	}

	@Override
	public ILogFileConfig logFileConfig() {
		return logFileConfig;
	}
}
