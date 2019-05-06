package com.ycr.kernel.mvp.connector;

import com.ycr.kernel.mvp.presenter.IMvpPresenter;

/**
 * Created by yuchengren on 2018/7/27.
 */
public interface IMvpConnector {
	void injectPresenter(IMvpPresenter mvpPresenter);
	void destroyPresenter();
}
