package com.ycr.kernel.log.printer;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;

import com.ycr.kernel.log.config.IFileLogPrinterConfig;

/**
 * Created by yuchengren on 2018/7/13.
 */
public class FileLogPrinter extends AbstractLogPrinter {

	private LogHandler logHandler;

	public FileLogPrinter(@NonNull IFileLogPrinterConfig logFileConfig){
		logHandler = new LogHandler(LogHandlerThread.getInstance().getLooper(),logFileConfig);
	}

	@Override
	public void print(int level, String tag, String msg) {
		Message message = Message.obtain();
		Bundle bundle = new Bundle();
		bundle.putInt(LogHandler.LEVEL,level);
		bundle.putString(LogHandler.TAG,tag);
		bundle.putString(LogHandler.MESSAGE,msg);
		message.setData(bundle);
		logHandler.sendMessage(message);
	}

	@Override
	public int level() {
		return logHandler.logFileConfig.level();
	}
}
