package com.ycr.kernel.log.config;

import com.ycr.kernel.log.constants.LogLevel;

/**
 * Created by yuchengren on 2018/11/29.
 */
public class LogPrinterConfig implements ILogPrinterConfig {

	protected int level = LogLevel.VERBOSE;

	@Override
	public int level() {
		return this.level;
	}

	@Override
	public void setLevel(@LogLevel int level) {
		this.level = level;
	}
}
