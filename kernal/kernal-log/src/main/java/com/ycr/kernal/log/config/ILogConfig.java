package com.ycr.kernal.log.config;

import java.util.List;
import java.util.Set;

/**
 * Created by yuchengren on 2018/7/12.
 */
public interface ILogConfig {
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

	/**
	 * 日志打印机的类型
	 * @return
	 */
	Set<Integer> logPrinterTypes();

}
