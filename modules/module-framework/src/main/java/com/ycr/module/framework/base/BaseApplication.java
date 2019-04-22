package com.ycr.module.framework.base;

import android.support.annotation.CallSuper;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.ycr.kernel.http.okhttp.OkHttpScheduler;
import com.ycr.kernel.json.parse.gson.GsonJsonParser;
import com.ycr.kernel.union.UnionApplication;
import com.ycr.kernel.union.helper.UnionContainer;

import org.jetbrains.annotations.Nullable;

import okhttp3.OkHttpClient;

/**
 * Created by yuchengren on 2018/12/11.
 */
public class BaseApplication extends UnionApplication {

	@CallSuper
	@Override
	public void doInit(boolean isMainProcess, @Nullable String processName) {
		super.doInit(isMainProcess,processName);
		UnionContainer.jsonParser = GsonJsonParser.INSTANCE.doInit(new Gson(), new JsonParser());
		UnionContainer.httpScheduler = OkHttpScheduler.INSTANCE.doInit(UnionContainer.jsonParser, new OkHttpClient());
	}
}
