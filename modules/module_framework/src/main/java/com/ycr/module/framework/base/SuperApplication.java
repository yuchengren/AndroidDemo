package com.ycr.module.framework.base;

import android.support.annotation.CallSuper;

import com.ycr.kernel.union.UnionApplication;
import com.ycr.module.framework.helper.InjectHelper;
import com.ycr.module.framework.helper.SharePrefsHelper;
import com.ycr.module.framework.view.AppLayoutInflaterFactoryCreator;

import org.jetbrains.annotations.Nullable;

/**
 * Created by yuchengren on 2018/12/11.
 */
public class SuperApplication extends UnionApplication {

	@CallSuper
	@Override
	public void doInit(boolean isMainProcess, @Nullable String processName) {
		super.doInit(isMainProcess,processName);
		SharePrefsHelper.getInstance().init(this);
		InjectHelper.layoutInflaterFactoryCreator = new AppLayoutInflaterFactoryCreator();
	}
}
