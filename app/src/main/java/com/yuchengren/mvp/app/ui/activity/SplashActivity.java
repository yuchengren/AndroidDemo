package com.yuchengren.mvp.app.ui.activity;

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

import com.yuchengren.mvp.R;
import com.yuchengren.mvp.app.ui.activity.Base.SuperActivity;
import com.yuchengren.mvp.constant.Constants;
import com.yuchengren.mvp.util.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchengren on 2017/11/10.
 */

public class SplashActivity extends SuperActivity {

	private List<String> mRequestPermissionList = new ArrayList<>();

	public static final int REQUEST_PERMISSION_CODE = 1;

	@Override
	protected int getLayoutResID() {
		return R.layout.activity_splash;
	}

	@Override
	protected void initViews() {

	}

	@Override
	protected void initListeners() {

	}

	@Override
	protected void initData() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		checkPermissions();

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
}
