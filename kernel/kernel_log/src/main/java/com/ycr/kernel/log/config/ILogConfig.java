package com.ycr.kernel.log.config;

import android.content.Context;

import com.ycr.kernel.log.printer.ILogPrinter;

import java.util.Set;

/**
 * Created by yuchengren on 2018/7/12.
 */
public interface ILogConfig {
	/**
	 * 上下文
	 */
	Context context();
	/**
	 * 获取tag的前缀
	 * @return
	 */
	String tagPre();

	/**
	 * 获取log的等级
	 * @return
	 */
	int level();

	/**
	 * 控制日志是否输出
	 * @return
	 */
	boolean enabled();
}
