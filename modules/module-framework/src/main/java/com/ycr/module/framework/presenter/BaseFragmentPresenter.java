package com.ycr.module.framework.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ycr.kernel.mvp.view.IFragmentSpecialLifeCycle;
import com.ycr.kernel.mvp.view.IMvpView;

/**
 * Created by yuchengren on 2018/12/10.
 */
public abstract class BaseFragmentPresenter<V extends IMvpView> extends BasePresenter<V> implements IFragmentSpecialLifeCycle{

	public BaseFragmentPresenter(V mvpView) {
		super(mvpView);
	}

	@Override
	public void onAttach() {

	}

	@Override
	public void onCreateView(@Nullable Bundle savedInstanceState) {

	}

	@Override
	public void onViewCreated(@Nullable Bundle savedInstanceState) {

	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {

	}

	@Override
	public void onDestroyView() {

	}

	@Override
	public void onDetach() {

	}
}
