package com.ycr.kernel.mvp.connector;

import android.content.Intent;
import android.os.Bundle;

import com.ycr.kernel.mvp.presenter.IMvpPresenter;
import com.ycr.kernel.mvp.view.IBaseLifeCycle;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuchengren on 2018/7/27.
 */
public class LifeCycleMvpConnector extends MvpConnector implements IBaseLifeCycle{
	private Intent intent;
	private Set<IBaseLifeCycle> baseLifeCycleSet;

	public LifeCycleMvpConnector(){
		super();
		baseLifeCycleSet = new HashSet<>();
	}

	@Override
	public void injectPresenter(IMvpPresenter mvpPresenter) {
		super.injectPresenter(mvpPresenter);
		if(mvpPresenter instanceof IBaseLifeCycle){
			baseLifeCycleSet.add((IBaseLifeCycle)mvpPresenter);
		}
	}

	@Override
	public void destroyPresenter() {
		super.destroyPresenter();
		baseLifeCycleSet.clear();
	}

	@Override
	final public Intent getIntent() {
		return intent;
	}

	@Override
	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	@Override
	public void onCreated(Bundle savedInstanceState) {
		for (IBaseLifeCycle baseLifeCycle : baseLifeCycleSet) {
			baseLifeCycle.onCreated(savedInstanceState);
		}
	}

	@Override
	public void onStart() {
		for (IBaseLifeCycle baseLifeCycle : baseLifeCycleSet) {
			baseLifeCycle.onStart();
		}
	}

	@Override
	public void onResume() {
		for (IBaseLifeCycle baseLifeCycle : baseLifeCycleSet) {
			baseLifeCycle.onResume();
		}
	}

	@Override
	public void onPause() {
		for (IBaseLifeCycle baseLifeCycle : baseLifeCycleSet) {
			baseLifeCycle.onPause();
		}
	}

	@Override
	public void onStop() {
		for (IBaseLifeCycle baseLifeCycle : baseLifeCycleSet) {
			baseLifeCycle.onStop();
		}
	}

	@Override
	public void onDestroy() {
		for (IBaseLifeCycle baseLifeCycle : baseLifeCycleSet) {
			baseLifeCycle.onDestroy();
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		for (IBaseLifeCycle baseLifeCycle : baseLifeCycleSet) {
			baseLifeCycle.onSaveInstanceState(outState);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		for (IBaseLifeCycle baseLifeCycle : baseLifeCycleSet) {
			baseLifeCycle.onActivityResult(requestCode,resultCode,data);
		}
	}
}
