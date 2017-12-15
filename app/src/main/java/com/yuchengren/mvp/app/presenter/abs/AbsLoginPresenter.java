package com.yuchengren.mvp.app.presenter.abs;

import com.yuchengren.mvp.app.model.LoginModel;
import com.yuchengren.mvp.app.view.ILoginView;

/**
 * Created by yuchengren on 2016/9/18.
 */
public abstract class AbsLoginPresenter extends Presenter<ILoginView,LoginModel> {

    public abstract void login(String username, String password) ;
}
