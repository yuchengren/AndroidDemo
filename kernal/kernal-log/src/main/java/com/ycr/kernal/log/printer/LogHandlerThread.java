package com.ycr.kernal.log.printer;

import android.os.HandlerThread;

/**
 * Created by yuchengren on 2018/7/16.
 */
public class LogHandlerThread extends HandlerThread{

	public static final String LOG = "LOG";
	public static LogHandlerThread instance;

	private LogHandlerThread() {
		super(LOG);
	}

	public static LogHandlerThread getInstance(){
		if(instance == null){
			synchronized (LogHandlerThread.class){
				if(instance == null){
					instance = new LogHandlerThread();
					instance.start();
				}
			}
		}
		return instance;
	}



}
