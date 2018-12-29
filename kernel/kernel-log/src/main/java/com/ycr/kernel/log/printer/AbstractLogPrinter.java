package com.ycr.kernel.log.printer;

import com.ycr.kernel.log.constants.LogLevel;

/**
 * Created by yuchengren on 2018/11/29.
 */
public abstract class AbstractLogPrinter implements ILogPrinter {

	@Override
	public int level() {
		return LogLevel.VERBOSE;
	}
}
