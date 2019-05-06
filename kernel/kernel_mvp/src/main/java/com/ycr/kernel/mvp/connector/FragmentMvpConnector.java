package com.ycr.kernel.mvp.connector;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ycr.kernel.mvp.presenter.IMvpPresenter;
import com.ycr.kernel.mvp.view.IFragmentSpecialLifeCycle;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuchengren on 2018/7/27.
 */
public class FragmentMvpConnector extends LifeCycleMvpConnector implements IFragmentSpecialLifeCycle {

	private Set<IFragmentSpecialLifeCycle>  fragmentSpecialLifeCycleSet;

	public FragmentMvpConnector(){
		super();
		fragmentSpecialLifeCycleSet = new HashSet<>();
	}

	@Override
	public void injectPresenter(IMvpPresenter mvpPresenter) {
		super.injectPresenter(mvpPresenter);
		if(mvpPresenter instanceof IFragmentSpecialLifeCycle){
			fragmentSpecialLifeCycleSet.add((IFragmentSpecialLifeCycle)mvpPresenter);
		}
	}

	@Override
	public void destroyPresenter() {
		super.destroyPresenter();
		fragmentSpecialLifeCycleSet.clear();
	}

	@Override
	public void onAttach() {
		for (IFragmentSpecialLifeCycle fragmentSpecialLifeCycle : fragmentSpecialLifeCycleSet) {
			fragmentSpecialLifeCycle.onAttach();
		}
	}

	@Override
	public void onCreateView(@Nullable Bundle savedInstanceState) {
		for (IFragmentSpecialLifeCycle fragmentSpecialLifeCycle : fragmentSpecialLifeCycleSet) {
			fragmentSpecialLifeCycle.onCreateView(savedInstanceState);
		}
	}

	@Override
	public void onViewCreated(@Nullable Bundle savedInstanceState) {
		for (IFragmentSpecialLifeCycle fragmentSpecialLifeCycle : fragmentSpecialLifeCycleSet) {
			fragmentSpecialLifeCycle.onViewCreated(savedInstanceState);
		}
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		for (IFragmentSpecialLifeCycle fragmentSpecialLifeCycle : fragmentSpecialLifeCycleSet) {
			fragmentSpecialLifeCycle.onViewCreated(savedInstanceState);
		}
	}

	@Override
	public void onDestroyView() {
		for (IFragmentSpecialLifeCycle fragmentSpecialLifeCycle : fragmentSpecialLifeCycleSet) {
			fragmentSpecialLifeCycle.onDestroyView();
		}
	}

	@Override
	public void onDetach() {
		for (IFragmentSpecialLifeCycle fragmentSpecialLifeCycle : fragmentSpecialLifeCycleSet) {
			fragmentSpecialLifeCycle.onDetach();
		}
	}
}
