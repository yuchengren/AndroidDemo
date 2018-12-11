package com.ycr.module.framework.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.ycr.kernel.union.mvp.UnionFragment;

import butterknife.ButterKnife;

/**
 * Created by yuchengren on 2018/12/10.
 */
public abstract class BaseFragment extends UnionFragment {

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

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		doInject();
	}

	private void doInject() {

	}

	protected boolean isSupportButterKnife(){
		return true;
	}
}
