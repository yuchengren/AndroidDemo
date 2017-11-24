package com.yuchengren.mvp.util;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.yuchengren.mvp.app.MvpApplication;

/**
 * Created by yuchengren on 2017/11/24.
 */

public class AndroidUtil {
	/**
	 * 检查是否有SIM卡
	 * @return
	 */
	public static boolean isHasSIMCard(Context context){
		boolean isHasSIMCard = true;
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		int callState = telephonyManager.getCallState();
		switch (callState){
			case TelephonyManager.SIM_STATE_ABSENT://无卡
			case TelephonyManager.SIM_STATE_UNKNOWN://未知状态
				isHasSIMCard = false;
				break;
			default:
				break;
		}
		return isHasSIMCard;
	}
}
