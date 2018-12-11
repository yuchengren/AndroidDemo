package com.yuchengren.mvp.app.body.splash;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.yuchengren.mvp.R;
import com.yuchengren.mvp.app.body.main.MainActivity;
import com.yuchengren.mvp.constant.Constants;
import com.yuchengren.mvp.constant.MenuCode;
import com.yuchengren.mvp.constant.SharePrefsKey;
import com.yuchengren.mvp.entity.db.MenuEntity;
import com.yuchengren.mvp.greendao.gen.MenuEntityDao;
import com.yuchengren.mvp.util.PermissionUtil;
import com.yuchengren.mvp.util.SharePrefsUtil;
import com.yuchengren.mvp.util.business.DaoHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchengren on 2017/11/10.
 */

public class SplashActivity extends AppCompatActivity {

	private List<String> mRequestPermissionList = new ArrayList<>();

	public static final int REQUEST_PERMISSION_CODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		checkPermissions();
		if(SharePrefsUtil.getInstance().getBoolean(SharePrefsKey.IS_FIRST_LOGIN,true)){
			insertMenu();
		}
		SharePrefsUtil.getInstance().putBoolean(SharePrefsKey.IS_FIRST_LOGIN,false);
	}

	private void checkPermissions() {
		//如果系统版本大于或等于6.0
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
			for (String permission : PermissionUtil.mPermissionArray) {
				if(PackageManager.PERMISSION_GRANTED != checkPermission(permission, Process.myPid(),Process.myUid())){
					mRequestPermissionList.add(permission);
				}
			}
			if(!mRequestPermissionList.isEmpty()){
				requestPermissions(mRequestPermissionList.toArray(new String[mRequestPermissionList.size()]),REQUEST_PERMISSION_CODE);
			}else{
				startMainActivity();
			}
		}else{
			startMainActivity();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
			return;
		}
		if(requestCode == REQUEST_PERMISSION_CODE){
			for (int i = 0; i < grantResults.length; i++) {
				for (int j = mRequestPermissionList.size() - 1; j >= 0; j--) {
					if(grantResults[i] == PackageManager.PERMISSION_GRANTED
							&& permissions[i].equals(mRequestPermissionList.get(j))){
						mRequestPermissionList.remove(j);
						break;
					}
				}
			}
		}
		if(!mRequestPermissionList.isEmpty()){
			new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("应用运行所需权限未全部获取到！")
					.setNegativeButton("退出应用", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					}).setPositiveButton("去设置", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					startAppSettings();
				}
			}).setCancelable(false).show();

		}else{
			startMainActivity();
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	private void startMainActivity(){
		startActivity(new Intent(this, MainActivity.class));
	}

	// 启动应用的设置
	private void startAppSettings() {
		Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		intent.setData(Uri.parse(Constants.PACKAGE_URL_SCHEME + getPackageName()));
		startActivity(intent);
	}

	private void insertMenu() {
		MenuEntityDao menuEntityDao = DaoHelper.getMenuEntityDao();

		MenuEntity homeMenuEntity = new MenuEntity();
		homeMenuEntity.setName(getString(R.string.home_page));
		homeMenuEntity.setCode(MenuCode.First.HOME);
		homeMenuEntity.setParentCode(MenuCode.TOP);
		menuEntityDao.insert(homeMenuEntity);

		MenuEntity otherMenuEntity = new MenuEntity();
		otherMenuEntity.setName(getString(R.string.other));
		otherMenuEntity.setCode(MenuCode.First.OTHER);
		otherMenuEntity.setParentCode(MenuCode.TOP);
		menuEntityDao.insert(otherMenuEntity);

		MenuEntity callPhoneBackMenuEntity = new MenuEntity();
		callPhoneBackMenuEntity.setName(getString(R.string.call_phone_back));
		callPhoneBackMenuEntity.setCode(MenuCode.Second.CALL_PHONE_BACK);
		callPhoneBackMenuEntity.setParentCode(MenuCode.First.HOME);
		menuEntityDao.insert(callPhoneBackMenuEntity);

		MenuEntity rxAndroidBackMenuEntity = new MenuEntity();
		rxAndroidBackMenuEntity.setName(getString(R.string.rx_android));
		rxAndroidBackMenuEntity.setCode(MenuCode.Second.RX_ANDROID);
		rxAndroidBackMenuEntity.setParentCode(MenuCode.First.HOME);
		menuEntityDao.insert(rxAndroidBackMenuEntity);

		MenuEntity testMenuEntity = new MenuEntity();
		testMenuEntity.setName(getString(R.string.test));
		testMenuEntity.setCode(MenuCode.Second.TEST);
		testMenuEntity.setParentCode(MenuCode.First.HOME);
		menuEntityDao.insert(testMenuEntity);

		MenuEntity skinMenuEntity = new MenuEntity();
		skinMenuEntity.setName(getString(R.string.theme_switch));
		skinMenuEntity.setCode(MenuCode.Second.THEME_SWITCH);
		skinMenuEntity.setParentCode(MenuCode.First.HOME);
		menuEntityDao.insert(skinMenuEntity);

		MenuEntity imageEditMenuEntity = new MenuEntity();
		imageEditMenuEntity.setName(getString(R.string.image_edit));
		imageEditMenuEntity.setCode(MenuCode.Second.IMAGE_EDIT);
		imageEditMenuEntity.setParentCode(MenuCode.First.HOME);
		menuEntityDao.insert(imageEditMenuEntity);

	}
}
