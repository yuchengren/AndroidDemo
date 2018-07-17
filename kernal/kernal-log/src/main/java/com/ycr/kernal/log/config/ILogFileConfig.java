package com.ycr.kernal.log.config;

/**
 * Created by yuchengren on 2018/7/16.
 */
public interface ILogFileConfig {
	/**
	 * 文件根路径
	 * @return
	 */
	String fileRootPath();

	/**
	 * 最大缓存总大小 字节数
	 * @return
	 */
	long maxTotalCacheSize();
	/**
	 * 文件命名的时间格式
	 */
	String fileNameDateFormat();
}
