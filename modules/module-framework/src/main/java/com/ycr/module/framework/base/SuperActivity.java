package com.ycr.module.framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ycr.kernel.union.mvp.UnionActivity;

import butterknife.ButterKnife;

/**
 * Created by yuchengren on 2018/12/10.
 */
public abstract class SuperActivity extends UnionActivity {

	@Override
	public void beforeBindView() {

	}

	@Override
	public void bindView(View rootView) {
		if(isSupportButterKnife()){
			ButterKnife.bind(this,rootView);
		}
	}

	@Override
	public void afterBindView(View rootView, Bundle savedInstanceState) {

	}

	protected boolean isSupportButterKnife(){
		return true;
	}

	protected boolean isSupportDagger(){
		return false;
	}

	@Override
	protected void beforeCreate(@Nullable Bundle savedInstanceState) {
		super.beforeCreate(savedInstanceState);
		doInject();
	}

	protected void doInject() {
		if(isSupportDagger()){

		}
	}
}
