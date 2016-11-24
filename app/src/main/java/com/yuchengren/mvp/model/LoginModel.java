package com.yuchengren.mvp.model;

import com.yuchengren.mvp.Util.OkHttpUtil;
import com.yuchengren.mvp.constant.Url;
import com.yuchengren.mvp.interfaces.callback.RequestCallBack;
import com.yuchengren.mvp.interfaces.listener.OnLoginListener;
import com.yuchengren.mvp.model.abs.AbsLoginModel;

/**
 * Created by yuchengren on 2016/9/18.
 */
public  class LoginModel extends AbsLoginModel {

    @Override
    public void login(String username, String password, final OnLoginListener loginListener) {

        OkHttpUtil.request(Url.LOGIN_URL, null, new RequestCallBack() {
            @Override
            public void onSuccess() {
                if(loginListener != null){
                    loginListener.onSuccess();
                }
            }

            @Override
            public void onFail() {
                if(loginListener != null){
                    loginListener.onFail();
                }

            }
        });

    }
}
