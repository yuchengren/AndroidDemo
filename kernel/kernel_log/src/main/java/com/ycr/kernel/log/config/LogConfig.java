package com.ycr.kernel.log.config;

import android.content.Context;

import com.ycr.kernel.log.constants.LogLevel;
import com.ycr.kernel.log.printer.ILogPrinter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuchengren on 2018/7/12.
 */
public class LogConfig implements ILogConfig{

	private Context context;
	private String tagPre;
	private boolean enabled = true;
	private int level;
	private Set<ILogPrinter> logPrinters;

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

	public LogConfig setTagPre(String tagPre) {
		this.tagPre = tagPre;
		return this;
	}

	public LogConfig setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public LogConfig setLevel(int level) {
		this.level = level;
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


}
