package com.yuchengren.mvp.model.abs;

import com.yuchengren.mvp.interfaces.listener.OnLoginListener;

/**
 * Created by yuchengren on 2016/9/18.
 */
public abstract class AbsLoginModel extends Model {

    public abstract void login(String username,String password,OnLoginListener onLoginListener);
}
