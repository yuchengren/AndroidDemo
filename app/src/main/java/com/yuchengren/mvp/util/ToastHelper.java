package com.yuchengren.mvp.util;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yuchengren.mvp.R;
import com.yuchengren.mvp.app.MvpApplication;


/**
 * Toast工具类
 * Created by yuchengren on 2017/2/3.
 */

public class ToastHelper {
	private static Toast mToast;
	private static long firstTime;
	private static String showMessage ="";

	/**
	 * show Toast，不重复新建Toast对象
	 * @param message
	 * @param duration
	 */
	public static void show(String message, int duration){
		if(mToast == null){
			initToast(message);
			mToast.setDuration(duration);
			mToast.show();
			firstTime = System.currentTimeMillis();
			showMessage = message;
		}else{
			long secondTime =  System.currentTimeMillis();
			if(showMessage.equals(message)){
				if(secondTime - firstTime > mToast.getDuration()){
					mToast.setDuration(duration);
					mToast.show();
				}
			}else {
				mToast.setDuration(duration);
				((TextView)mToast.getView().findViewById(R.id.tv_toast)).setText(message);
				mToast.show();
				showMessage = message;
			}
			firstTime = secondTime;
		}
	}
	public static void show(int resId, int duration){
		show(MvpApplication.getInstance().getResources().getString(resId),duration);
	}

	public static void show(String message){
		show(message, Toast.LENGTH_SHORT);
	}

	public static void show(int resId){
		show(resId,Toast.LENGTH_SHORT);
	}

	/**
	 * 初始化Toast的自定义布局，显示位置等
	 * @param message
	 */
	private static void initToast(String message) {
		View view = View.inflate(MvpApplication.getInstance(), R.layout.toast,null);
		TextView tv_toast = (TextView) view.findViewById(R.id.tv_toast);
		int h = MvpApplication.getInstance().getResources().getDisplayMetrics().heightPixels;
		int l = MvpApplication.getInstance().getResources().getDisplayMetrics().widthPixels;
		tv_toast.setMinWidth(l / 4);
		tv_toast.setText(message);

		mToast = new Toast(MvpApplication.getInstance());
		mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		mToast.setView(view);
	}


}
