package com.ycr.module.framework.presenter;

import android.content.Intent;

import com.ycr.kernel.mvp.view.IActivitySpecialLifeCycle;
import com.ycr.kernel.mvp.view.IMvpView;

/**
 * Created by yuchengren on 2018/12/10.
 */
public abstract class BaseActivityPresenter<view extends IMvpView> extends BasePresenter<view> implements IActivitySpecialLifeCycle {

	public BaseActivityPresenter(view mvpView) {
		super(mvpView);
	}

	@Override
	public void onRestart() {

	}

	@Override
	public void onNewIntent(Intent intent) {

	}
}
