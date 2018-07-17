package com.ycr.kernal.log.printer;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;

import com.ycr.kernal.log.config.ILogFileConfig;
import com.ycr.kernal.log.config.LogFileConfig;

/**
 * Created by yuchengren on 2018/7/16.
 */
public class LogHandler extends android.os.Handler {

	public static final String LEVEL = "level";
	public static final String TAG = "tag";
	public static final String MESSAGE = "message";

	private Context context;
	private ILogFileConfig logFileConfig;

	public LogHandler(Looper looper, Context context, ILogFileConfig logFileConfig) {
		super(looper);
		this.context = context.getApplicationContext();
		if(logFileConfig == null){
			logFileConfig = LogFileConfig.create(context);
		}
		this.logFileConfig = logFileConfig;
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		Bundle bundle = msg.getData();
		if(context != null){
			FileLogHelper.getInstance(logFileConfig)
					.write(bundle.getInt(LEVEL),bundle.getString(TAG),bundle.getString(MESSAGE));
		}
	}
}
