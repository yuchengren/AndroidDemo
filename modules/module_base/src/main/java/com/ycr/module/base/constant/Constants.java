package com.ycr.module.base.constant;


import com.ycr.module.base.util.AndroidUtil;

/**
 * Created by yuchengren on 2017/11/28.
 */

public interface Constants {

	int PAGE_COUNT = 20; //分页的一页加载数量

	String APP_FILE_NAME = "MVP";
	// android数据目录
	String DATA_PATH = "/data/data/com.yuchengren.mvp";
	String SDCARD_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
	String PEKON_ARCFACE_PATH = AndroidUtil.isHasSdcard() ? SDCARD_PATH + "/"+APP_FILE_NAME :DATA_PATH + "/"+APP_FILE_NAME;

	String LOG_PATH = PEKON_ARCFACE_PATH +"/logs";
	String CRASH_PATH = PEKON_ARCFACE_PATH + "/crash";

	String DB_NAME = "mvp-db";
	String PACKAGE_URL_SCHEME = "package:";

}
