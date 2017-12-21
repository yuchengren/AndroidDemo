package com.yuchengren.mvp.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

/**
 * Created by yuchengren on 2017/11/24.
 */

public class AndroidUtil {
	/**
	 * 检查是否有SIM卡
	 * @return
	 */
	public static boolean isHasSIMCard(Context context) {
		boolean isHasSIMCard = true;
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		int callState = telephonyManager.getCallState();
		switch (callState) {
			case TelephonyManager.SIM_STATE_ABSENT://无卡
			case TelephonyManager.SIM_STATE_UNKNOWN://未知状态
				isHasSIMCard = false;
				break;
			default:
				break;
		}
		return isHasSIMCard;
	}

	/**
	 * 判断sd卡是否安装
	 *
	 * @return
	 */
	public static boolean isHasSdcard() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	public static String getIMEI(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
			return "";
		}
		String deviceId = tm.getDeviceId();
		if(deviceId == null){
			deviceId = "";
		}
		return deviceId;
	}



}
