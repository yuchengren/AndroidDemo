package com.ycr.kernal.log.constants;

import android.support.annotation.IntDef;

import static com.ycr.kernal.log.constants.LogPrinterType.CONSOLE;
import static com.ycr.kernal.log.constants.LogPrinterType.FILE;
import static com.ycr.kernal.log.constants.LogPrinterType.NETWORK;

/**
 * 日志输出的
 * Created by yuchengren on 2018/7/12.
 */
@IntDef({CONSOLE,FILE,NETWORK})
public @interface LogPrinterType {
	/**
	 * 控制台输出
	 */
	int CONSOLE = 0;
	/**
	 * 文件输出
	 */
	int FILE = 1;
	/**
	 * 网络上传输出
	 */
	int NETWORK = 2;
}
