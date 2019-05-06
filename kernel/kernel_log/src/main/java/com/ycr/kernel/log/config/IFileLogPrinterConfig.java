package com.ycr.kernel.log.config;

import android.content.Context;

/**
 * Created by yuchengren on 2018/7/16.
 */
public interface IFileLogPrinterConfig extends ILogPrinterConfig{
	Context context();
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
	 * 打印文件的上层文件夹命名的时间格式
	 */
	String folderNameDateFormat();
	/**
	 * 文件命名的时间格式
	 */
	String fileNameDateFormat();
}
