package com.yuchengren.mvp.util;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.yuchengren.mvp.app.ui.activity.SplashActivity;
import com.yuchengren.mvp.constant.Constants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Created by yuchengren on 2017/12/15.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
	private static final String TAG = CrashHandler.class.getName();

	private static CrashHandler instance;

	private Context mContext;
	private Thread.UncaughtExceptionHandler mDefaultHandler;

	private CrashHandler(){

	}

	public static CrashHandler getInstance(){
		if(instance == null){
			synchronized (CrashHandler.class){
				if(instance == null){
					instance = new CrashHandler();
				}
			}
		}
		return instance;
	}

	public void init(Context context){
		this.mContext = context;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 注册未捕获的异常处理者后，当UncaughtException发生时，会回调此方法
	 * @param t
	 * @param e
	 */
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		if(!handleException(e) && mDefaultHandler != null){
			mDefaultHandler.uncaughtException(t,e);
		}else{
			//线程sleep一会，用于展示提示信息给用户，再退出程序
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			// 1秒钟后重启应用
//			Intent intent = new Intent(mContext, SplashActivity.class);
//			@SuppressLint("WrongConstant") PendingIntent restartIntent = PendingIntent.getActivity(mContext, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
//			AlarmManager am = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
//			am.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,restartIntent);

			Log.e(TAG,e.toString());
			Process.killProcess(Process.myPid());
			System.exit(10);
		}

	}

	public boolean handleException(Throwable e){
		if(e == null){
			return true;
		}
		LogHelper.e(TAG,e.toString());
		final String message = e.getMessage();
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(mContext,"程序发生异常：message" + message,Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}).start();
		saveCrashInfoInFile(e);
		return true;
	}

	private void saveCrashInfoInFile(Throwable e) {
		File crashPath = new File(Constants.CRASH_PATH);
		if(!crashPath.exists()){
			crashPath.mkdirs();
		}
		File crashFile = new File(crashPath, "crash-" + DateHelper.getCurrentTime("yyyy-MM-dd_HH:mm:ss") + ".txt");
		try {
			if(!crashFile.exists()){
				crashFile.createNewFile();
			}
			PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(crashFile, true)));
			Throwable cause = e.getCause();
			while (cause != null){
				printWriter.println(cause.toString());
				cause = e.getCause();
			}
			printWriter.println("STACK_TRACE=" + e.toString());

			PackageManager packageManager = mContext.getPackageManager();
			try {
				PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
				printWriter.println("versionCode=" + String.valueOf(packageInfo.versionCode));
				printWriter.println("versionName=" + String.valueOf(packageInfo.versionName));
			} catch (PackageManager.NameNotFoundException e2) {
				e2.printStackTrace();
			}

			Properties deviceProperties = getDeviceProperties();
			deviceProperties.store(printWriter,"");

			printWriter.flush();
			printWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private Properties getDeviceProperties() {
		Properties properties = new Properties();

		Field[] declaredFields = Build.class.getDeclaredFields();
		for (Field declaredField : declaredFields) {
			if(!declaredField.isAccessible()){
				declaredField.setAccessible(true);
			}
			try {
				properties.put(declaredField.getName(),String.valueOf(declaredField.get(null)));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}
}
