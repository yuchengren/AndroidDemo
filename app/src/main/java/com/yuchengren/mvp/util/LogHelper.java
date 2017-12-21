package com.yuchengren.mvp.util;

import android.util.Log;


import com.yuchengren.mvp.constant.Constants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by yuchengren on 2017/12/5.
 */
public class LogHelper {
	//是否需要打印日志
	public static boolean isDebug = true;
	/*写入文件的日志级别 */
	public static int mLogInFileLevel = Log.INFO;

	public static void v(String tag, String msg){
		if(isDebug){
			Log.v(tag,msg);
			if(Log.VERBOSE >= mLogInFileLevel){
				writeInFile(Log.VERBOSE,tag,msg);
			}
		}
	}
	public static void d(String tag, String msg){
		if(isDebug){
			Log.d(tag,msg);
			if(Log.DEBUG  >= mLogInFileLevel){
				writeInFile(Log.DEBUG,tag,msg);
			}
		}
	}
	public static void i(String tag, String msg){
		if(isDebug){
			Log.i(tag,msg);
			if(Log.INFO  >= mLogInFileLevel){
				writeInFile(Log.INFO,tag,msg);
			}
		}
	}
	public static void w(String tag, String msg){
		if(isDebug){
			Log.w(tag,msg);
			if(Log.WARN  >= mLogInFileLevel){
				writeInFile(Log.WARN,tag,msg);
			}
		}
	}
	public static void e(String tag, String msg){
		if(isDebug){
			Log.e(tag,msg);
			if(Log.ERROR  >= mLogInFileLevel){
				writeInFile(Log.ERROR,tag,msg);
			}
		}
	}


	private static void writeInFile(int logLevel, String tag, String msg){
		String currentDay = DateHelper.getCurrentDayTime();
		File logPath = new File(Constants.LOG_PATH);
		if(!logPath.exists()){
			logPath.mkdirs();
		}
		try {
			File filePath = new File(logPath,currentDay + ".txt");
			PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
			printWriter.println(DateHelper.getCurrentMillisTime() + " " + getLogLevelString(logLevel) + "/" + tag + ":" + msg);
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getLogLevelString(int logLevel){
		String logLevelString = "";
		switch (logLevel){
			case Log.VERBOSE:
				logLevelString ="V";
				break;
			case Log.DEBUG:
				logLevelString ="D";
				break;
			case Log.INFO:
				logLevelString ="I";
				break;
			case Log.WARN:
				logLevelString ="W";
				break;
			case Log.ERROR:
				logLevelString ="E";
				break;
			case Log.ASSERT:
				logLevelString ="A";
				break;
			default:
				break;
		}
		return logLevelString;
	}





}
