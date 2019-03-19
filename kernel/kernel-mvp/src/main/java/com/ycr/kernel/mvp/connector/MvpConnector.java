package com.ycr.kernel.mvp.connector;

import com.ycr.kernel.mvp.presenter.IMvpPresenter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuchengren on 2018/7/27.
 */
public class MvpConnector implements IMvpConnector {

	private Set<IMvpPresenter> mvpPresenterSet;

	public MvpConnector(){
		mvpPresenterSet = new HashSet<>();
	}

	@Override
	public void injectPresenter(IMvpPresenter mvpPresenter) {
		mvpPresenterSet.add(mvpPresenter);
	}

	@Override
	public void destroyPresenter() {
		for (IMvpPresenter iMvpPresenter : mvpPresenterSet) {
			iMvpPresenter.destroyView();
		}
		mvpPresenterSet.clear();
	}
}
