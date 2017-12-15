package com.yuchengren.mvp.app.ui.activity;

import android.os.Bundle;

import com.yuchengren.mvp.factory.ModelFactory;
import com.yuchengren.mvp.factory.PresenterFactory;
import com.yuchengren.mvp.app.model.LoginModel;
import com.yuchengren.mvp.app.presenter.LoginPresenter;

import com.yuchengren.mvp.app.ui.activity.Base.BaseActivity;
import com.yuchengren.mvp.app.view.ILoginView;

/**
 * Created by yuchengren on 2016/9/14.
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView {
    @Override
    protected int getLayoutResID() {
        return 0;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter(this,
                PresenterFactory.getInstance().getPresenter(LoginPresenter.class),
                ModelFactory.getInstance().getModel(LoginModel.class));
    }

    public void login(String username, String password){
        mPresenter.login(username,password);
    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFail() {

    }



}
