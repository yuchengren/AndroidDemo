package com.yuchengren.mvp.util.business;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.yuchengren.mvp.util.AndroidUtil;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by yuchengren on 2017/11/24.
 */

public class CallPhoneHelper {

	private static CallPhoneHelper mInstance;
	private TelephonyManager mTelephonyManager;
	private BackAfterCallPhoneListener mPhoneListener;

	private CallPhoneHelper(){

	}

	public static CallPhoneHelper getInstance(){
		if(mInstance == null){
			synchronized (CallPhoneHelper.class){
				if(mInstance == null){
					mInstance = new CallPhoneHelper();
				}
			}
		}
		return mInstance;
	}

	public void callPhoneNumber(Activity activity,String phoneNumber){
		if(AndroidUtil.isHasSIMCard(activity)){
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
			if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
				Toast.makeText(activity,"没有拨打电话的权限，请设置！",Toast.LENGTH_SHORT).show();
				return;
			}
			activity.startActivity(intent);
		}else{
			Toast.makeText(activity,"没有SIM卡，无法拨打电话！",Toast.LENGTH_SHORT).show();
		}
	}

	public void addPhoneStateListener(Activity activity) {
		mTelephonyManager = (TelephonyManager) activity.getSystemService(TELEPHONY_SERVICE);
		mPhoneListener = new BackAfterCallPhoneListener(activity);
		mTelephonyManager.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	private class BackAfterCallPhoneListener extends PhoneStateListener{

		private Activity activity;

		public BackAfterCallPhoneListener(Activity activity) {
			this.activity = activity;
		}

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state){
				case TelephonyManager.CALL_STATE_IDLE://空闲状态
					Intent intent = new Intent(activity,activity.getClass());
					activity.startActivity(intent);
					break;
				case TelephonyManager.CALL_STATE_RINGING://响铃状态
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK://通话状态
					break;
				default:
					break;
			}
			super.onCallStateChanged(state, incomingNumber);
		}
	}

	public void removePhoneStateListener(){
		if(mTelephonyManager != null || mPhoneListener != null){
			mTelephonyManager.listen(mPhoneListener,PhoneStateListener.LISTEN_NONE);
			mTelephonyManager = null;
			mPhoneListener = null;
		}
	}



}
