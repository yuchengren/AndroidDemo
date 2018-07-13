package com.ycr.kernal.log.printer;

import android.util.Log;

/**
 * Created by yuchengren on 2018/7/13.
 */
public class ConsoleLogPrinter implements ILogPrinter {
	@Override
	public void print(int level, String tag, String msg) {
		Log.println(level,tag,msg);
	}
}
