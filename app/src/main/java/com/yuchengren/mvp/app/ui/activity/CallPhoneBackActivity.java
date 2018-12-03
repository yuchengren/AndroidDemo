package com.yuchengren.mvp.app.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yuchengren.mvp.R;
import com.yuchengren.mvp.entity.People;
import com.yuchengren.mvp.util.GsonUtil;

import java.util.HashMap;

/**
 * Created by yuchengren on 2017/12/19.
 */

public class CallPhoneBackActivity extends AppCompatActivity implements View.OnClickListener{
	public final String TAG = getClass().getSimpleName();
	private EditText et_mobile_phone;
	private Button btn_call;
	private TelephonyManager mTelephonyManager;
	private BackAfterCallPhoneListener mPhoneListener;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_phone_back);
		et_mobile_phone = (EditText) findViewById(R.id.et_mobile_phone);
		btn_call = (Button) findViewById(R.id.btn_call);
		btn_call.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_call:
//				if(AndroidUtil.isHasSIMCard(this)){
//					callPhoneNumber(et_mobile_phone.getText().toString().toString().trim());
//					addPhoneStateListener();
//				}
				HashMap<Object, Object> map = new HashMap<>();
				map.put("sex", null);
				map.put("gendar","");
				Log.e(TAG, GsonUtil.parseJsonToBean(GsonUtil.formatObjectToJson(map),People.class).toString());
				break;
		}
	}

	/**
	 * 打电话
	 * @param phoneNumber
	 */
	private void callPhoneNumber(String phoneNumber) {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
			return;
		}
		startActivity(intent);
	}

	/**
	 * 添加电话状态监听器
	 */
	private void addPhoneStateListener() {
		mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		mPhoneListener = new BackAfterCallPhoneListener();
		mTelephonyManager.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	/**
	 * 电话状态监听器
	 */
	private class BackAfterCallPhoneListener extends PhoneStateListener{
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state){
				case TelephonyManager.CALL_STATE_IDLE://空闲状态
					//监测挂断，由通话状态转为空闲状态时，返回开启当前Activity
					//Activity的launchMode必须为singleTask或singleInstance,不然返回启动，会重新重建一个新的该Activity对象
					Intent intent = new Intent(CallPhoneBackActivity.this,CallPhoneBackActivity.class);
					startActivity(intent);
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

	/**
	 * 移除电话状态监听器，防止内存泄露
	 */
	private void removePhoneStateListener(){
		if(mTelephonyManager != null || mPhoneListener != null){
			mTelephonyManager.listen(mPhoneListener,PhoneStateListener.LISTEN_NONE);
			mTelephonyManager = null;
			mPhoneListener = null;
		}
	}

	@Override
	protected void onDestroy() {
		removePhoneStateListener();
		super.onDestroy();
	}
}
