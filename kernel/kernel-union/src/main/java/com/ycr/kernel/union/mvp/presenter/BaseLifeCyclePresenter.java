package com.ycr.kernel.union.mvp.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.ycr.kernel.mvp.view.IBaseLifeCycle;
import com.ycr.kernel.mvp.view.IMvpView;

/**
 * Created by yuchengren on 2018/12/3.
 */
public abstract class BaseLifeCyclePresenter<view extends IMvpView>  extends MvpPresenter<view> implements IBaseLifeCycle {

	public BaseLifeCyclePresenter(view mvpView) {
		super(mvpView);
	}

	@Override
	public void onCreated(Bundle savedInstanceState, Intent intent, Bundle bundle) {

	}

	@Override
	public void onStart() {

	}

	@Override
	public void onResume() {

	}

	@Override
	public void onPause() {

	}

	@Override
	public void onStop() {

	}

	@Override
	public void onDestroy() {

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

	}
}
