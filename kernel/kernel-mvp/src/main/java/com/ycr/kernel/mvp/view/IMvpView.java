package com.ycr.kernel.mvp.view;

import com.ycr.kernel.mvp.connector.IMvpConnector;

/**
 * Created by yuchengren on 2018/7/26.
 */
public interface IMvpView {
	 <C extends IMvpConnector> C getMvpConnector();
}
