package com.ycr.kernal.log.printer;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import com.ycr.kernal.log.config.ILogFileConfig;

/**
 * Created by yuchengren on 2018/7/13.
 */
public class FileLogPrinter implements ILogPrinter {

	private Context context;
	private LogHandler logHandler;

	public FileLogPrinter(Context context, ILogFileConfig logFileConfig){
		this.context = context;
		logHandler = new LogHandler(LogHandlerThread.getInstance().getLooper(),context,logFileConfig);
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
}
