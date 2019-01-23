package com.ycr.module.base.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by yuchengren on 2017/11/30.
 */

public class PermissionUtil {
	public static final String[] mPermissionArray ={
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.READ_PHONE_STATE,
			Manifest.permission.READ_EXTERNAL_STORAGE
	};

	public static boolean checkPermission(Context context, String permission){
		return ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED;
	}

}
