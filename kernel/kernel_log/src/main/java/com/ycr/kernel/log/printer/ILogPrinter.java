package com.ycr.kernel.log.printer;

import com.ycr.kernel.log.constants.LogLevel;

/**
 * 日志打印接口
 * Created by yuchengren on 2018/7/12.
 */
public interface ILogPrinter {
	/**
	 *打印日志
	 * @param level 日志级别
	 * @param tag	日志tag
	 * @param msg	日志内容
	 */
	void print(@LogLevel int level,String tag,String msg);

	/**
	 * 可打印日志的级别
	 */
	int level();
}
