package com.yuchengren.demo.util.business;

import android.content.Context;
import android.content.Intent;

import com.yuchengren.demo.app.other.CallPhoneBackActivity;
import com.yuchengren.demo.app.other.ImageEditActivity;
import com.yuchengren.demo.app.other.GridActivity;
import com.yuchengren.demo.app.other.RxAndroidActivity;
import com.yuchengren.demo.app.test.TestActivity;
import com.yuchengren.demo.app.other.ChangeThemeActivity;
import com.yuchengren.demo.constant.MapKey;
import com.yuchengren.demo.constant.MenuCode;
import com.yuchengren.demo.entity.db.MenuEntity;

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
			case MenuCode.Second.TEST:
				intent = new Intent(context, TestActivity.class);
				break;
			case MenuCode.Second.THEME_SWITCH:
				intent = new Intent(context, ChangeThemeActivity.class);
				break;
			case MenuCode.Second.IMAGE_EDIT:
				intent = new Intent(context, ImageEditActivity.class);
				break;
			case MenuCode.Second.NINE_GRID:
				intent = new Intent(context, GridActivity.class);
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
