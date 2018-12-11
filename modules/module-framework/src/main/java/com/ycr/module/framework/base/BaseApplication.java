package com.ycr.module.framework.base;

import android.support.annotation.CallSuper;

import com.ycr.kernel.union.UnionApplication;

import org.jetbrains.annotations.Nullable;

/**
 * Created by yuchengren on 2018/12/11.
 */
public class BaseApplication extends UnionApplication {

	@CallSuper
	@Override
	public void doInit(boolean isMainProcess, @Nullable String processName) {
		super.doInit(isMainProcess,processName);
	}
}
