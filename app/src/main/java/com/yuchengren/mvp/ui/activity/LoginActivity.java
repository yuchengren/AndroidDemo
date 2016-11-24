package com.yuchengren.mvp.ui.activity;

import com.yuchengren.mvp.factory.ModelFactory;
import com.yuchengren.mvp.factory.PresenterFactory;
import com.yuchengren.mvp.model.LoginModel;
import com.yuchengren.mvp.presenter.LoginPresenter;
import com.yuchengren.mvp.ui.activity.Base.BaseActivity;
import com.yuchengren.mvp.view.ILoginView;

/**
 * Created by yuchengren on 2016/9/14.
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView {
    @Override
    protected int getLayoutResID() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        setPresenter(PresenterFactory.getInstance().getPresenter(LoginPresenter.class));
        mPresenter.setView(this);
        mPresenter.setModle(ModelFactory.getInstance().getModel(LoginModel.class));
    }

    @Override
    protected void initListener() {

    }

    public void login(String username,String password){
        mPresenter.login(username,password);
    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFail() {

    }



}
