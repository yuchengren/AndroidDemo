package com.ycr.kernel.mvp.connector;

import android.content.Intent;

import com.ycr.kernel.mvp.presenter.IMvpPresenter;
import com.ycr.kernel.mvp.view.IActivitySpecialLifeCycle;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuchengren on 2018/7/27.
 */
public class ActivityMvpConnector extends LifeCycleMvpConnector implements IActivitySpecialLifeCycle {
	private Set<IActivitySpecialLifeCycle> activitySpecialLifeCycleSet;

	public ActivityMvpConnector(){
		super();
		activitySpecialLifeCycleSet = new HashSet<>();
	}

	@Override
	public void injectPresenter(IMvpPresenter mvpPresenter) {
		super.injectPresenter(mvpPresenter);
		if(mvpPresenter instanceof IActivitySpecialLifeCycle){
			activitySpecialLifeCycleSet.add((IActivitySpecialLifeCycle)mvpPresenter);
		}
	}

	@Override
	public void destroyPresenter() {
		super.destroyPresenter();
		activitySpecialLifeCycleSet.clear();
	}

	@Override
	public void onRestart() {
		for (IActivitySpecialLifeCycle activitySpecialLifeCycle : activitySpecialLifeCycleSet) {
			activitySpecialLifeCycle.onRestart();
		}
	}

	@Override
	public void onNewIntent(Intent intent) {
		for (IActivitySpecialLifeCycle activitySpecialLifeCycle : activitySpecialLifeCycleSet) {
			activitySpecialLifeCycle.onNewIntent(intent);
		}
	}
}
