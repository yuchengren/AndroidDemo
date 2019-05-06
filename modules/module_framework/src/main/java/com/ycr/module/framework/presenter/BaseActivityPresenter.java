package com.ycr.module.framework.presenter;

import android.content.Intent;

import com.ycr.kernel.mvp.view.IActivitySpecialLifeCycle;
import com.ycr.kernel.mvp.view.IMvpView;

/**
 * Created by yuchengren on 2018/12/10.
 */
public abstract class BaseActivityPresenter<V extends IMvpView> extends BasePresenter<V> implements IActivitySpecialLifeCycle {

	public BaseActivityPresenter(V mvpView) {
		super(mvpView);
	}

	@Override
	public void onRestart() {

	}

	@Override
	public void onNewIntent(Intent intent) {

	}
}
