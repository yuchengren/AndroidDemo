package com.ycr.kernel.log.printer;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;

import com.ycr.kernel.log.config.FileLogPrinterConfig;
import com.ycr.kernel.log.config.IFileLogPrinterConfig;

/**
 * Created by yuchengren on 2018/7/16.
 */
public class LogHandler extends android.os.Handler {

	public static final String LEVEL = "level";
	public static final String TAG = "tag";
	public static final String MESSAGE = "message";

	public IFileLogPrinterConfig logFileConfig;

	public LogHandler(Looper looper, IFileLogPrinterConfig logFileConfig) {
		super(looper);
		this.logFileConfig = logFileConfig;
	}

	public LogHandler(Looper looper, Context context) {
		super(looper);
		logFileConfig = FileLogPrinterConfig.create(context);
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		Bundle bundle = msg.getData();
		FileLogHelper.getInstance(logFileConfig).write(bundle.getInt(LEVEL),bundle.getString(TAG),bundle.getString(MESSAGE));
	}
}
