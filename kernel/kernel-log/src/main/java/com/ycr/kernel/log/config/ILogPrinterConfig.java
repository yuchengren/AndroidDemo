package com.ycr.kernel.log.config;

import com.ycr.kernel.log.constants.LogLevel;

/**
 * Created by yuchengren on 2018/11/29.
 */
public interface ILogPrinterConfig {
	/**
	 * 获取log的等级
	 * @return
	 */
	int level();

	ILogPrinterConfig setLevel(@LogLevel int level);
}
