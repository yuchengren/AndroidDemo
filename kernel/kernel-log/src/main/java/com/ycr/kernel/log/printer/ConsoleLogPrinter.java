package com.ycr.kernel.log.printer;

import android.util.Log;

/**
 * Created by yuchengren on 2018/7/13.
 */
public class ConsoleLogPrinter extends AbstractLogPrinter {

	@Override
	public void print(int level, String tag, String msg) {
		Log.println(level,tag,msg);
	}
}
