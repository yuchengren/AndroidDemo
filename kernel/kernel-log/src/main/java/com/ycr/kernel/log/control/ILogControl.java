package com.ycr.kernel.log.control;

import com.ycr.kernel.log.constants.LogLevel;
import com.ycr.kernel.log.constants.LogPrinterType;

import java.util.List;
import java.util.Set;

/**
 * Created by yuchengren on 2018/7/13.
 */
public interface ILogControl {

	String buildPrintTag(@LogPrinterType int logPrinterType, @LogLevel int level, String preTag, String moduleName, String tag);

	/**
	 * 构建生成输出的message
	 * @return
	 */
	String buildPrintMessage(String msg, Throwable tr, Object... args);

	/**
	 * 判断日志是否可打印
	 * @return
	 */
	boolean enabled(boolean configEnabled, @LogLevel int enabledLevel, @LogLevel int level, Set<Integer> logPrinterTypes);
}
