package com.ycr.kernel.mvp.presenter;

import com.ycr.kernel.mvp.view.IMvpView;

/**
 * Created by yuchengren on 2018/7/26.
 */
public interface IMvpPresenter<V extends IMvpView> {
	void injectView(V mvpView);
	void destroyView();
	V getView();
}
