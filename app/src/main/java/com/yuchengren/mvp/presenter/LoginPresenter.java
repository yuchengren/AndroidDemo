package com.yuchengren.mvp.presenter;

import com.yuchengren.mvp.interfaces.listener.OnLoginListener;
import com.yuchengren.mvp.presenter.abs.AbsLoginPresenter;

/**
 * Created by yuchengren on 2016/9/18.
 */
public class LoginPresenter extends AbsLoginPresenter {

    @Override
    public void login(String username, String password) {
        mModel.login(username, password, new OnLoginListener() {
            @Override
            public void onSuccess() {
                if(mView != null){
                    mView.onLoginSuccess();
                }
            }
            @Override
            public void onFail() {
                if(mView != null){
                    mView.onLoginFail();
                }
            }
        });
    }
}
