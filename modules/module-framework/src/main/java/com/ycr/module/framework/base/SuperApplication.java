package com.ycr.module.framework.base;

import android.support.annotation.CallSuper;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.ycr.kernel.http.okhttp.OkHttpScheduler;
import com.ycr.kernel.json.parse.gson.GsonJsonParser;
import com.ycr.kernel.union.UnionApplication;
import com.ycr.kernel.union.helper.UnionContainer;
import com.ycr.module.framework.helper.InjectHelper;
import com.ycr.module.framework.helper.SharePrefsHelper;
import com.ycr.module.framework.view.KklLayoutInflatorFactoryCreator;

import org.jetbrains.annotations.Nullable;

import okhttp3.OkHttpClient;

/**
 * Created by yuchengren on 2018/12/11.
 */
public class SuperApplication extends UnionApplication {

	@CallSuper
	@Override
	public void doInit(boolean isMainProcess, @Nullable String processName) {
		super.doInit(isMainProcess,processName);
		SharePrefsHelper.getInstance().init(this);
		InjectHelper.layoutInflaterFactoryCreator = new KklLayoutInflatorFactoryCreator();
	}
}
