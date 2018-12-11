package com.yuchengren.demo.app.body.login;

import com.ycr.kernel.mvp.connector.IMvpConnector;
import com.ycr.kernel.mvp.view.IMvpView;

/**
 * Created by yuchengren on 2018/12/11.
 */
public interface ILoginContract {

	interface IView extends IMvpView{
		void onLoginSuccess();
		void onLoginFail();
	}

	interface IPresenter{
		void login(String loginName,String password);
	}

	IView emptyView = new IView(){
		@Override
		public <C extends IMvpConnector> C getMvpConnector() {
			return null;
		}

		@Override
		public void onLoginSuccess() {

		}

		@Override
		public void onLoginFail() {

		}
	};

}
