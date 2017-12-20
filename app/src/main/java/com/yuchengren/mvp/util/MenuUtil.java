package com.yuchengren.mvp.util;

import android.content.Context;
import android.content.Intent;

import com.yuchengren.mvp.app.ui.activity.CallPhoneBackActivity;
import com.yuchengren.mvp.app.ui.activity.RxAndroidActivity;
import com.yuchengren.mvp.constant.MapKey;
import com.yuchengren.mvp.constant.MenuCode;
import com.yuchengren.mvp.entity.db.MenuEntity;

/**
 * Created by yuchengren on 2017/12/20.
 */

public class MenuUtil {

	public static void startActivity(Context context,MenuEntity menuEntity){
		if(menuEntity == null || menuEntity.getCode() == null){
			return;
		}
		Intent intent;
		switch (menuEntity.getCode()){
			case MenuCode.Second.CALL_PHONE_BACK:
				intent = new Intent(context, CallPhoneBackActivity.class);
				break;
			case MenuCode.Second.RX_ANDROID:
				intent = new Intent(context, RxAndroidActivity.class);
				break;
			default:
				intent = new Intent();
				break;
		}
		intent.putExtra(MapKey.CODE,menuEntity.getCode());
		intent.putExtra(MapKey.NAME,menuEntity.getName());
		context.startActivity(intent);
	}
}
