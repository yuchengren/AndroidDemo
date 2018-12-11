package com.yuchengren.mvp.app.body.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ycr.module.framework.base.BaseActivity;
import com.yuchengren.mvp.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yuchengren on 2016/9/14.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener,ILoginContract.IView{

    @BindView(R.id.btn_login)
    Button btn_login;

    @Override
    public int getRootLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    public void afterBindView(View rootView, Bundle savedInstanceState) {
        super.afterBindView(rootView, savedInstanceState);

    }

    @Override
    @OnClick(R.id.btn_login)
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:

                break;
        }
    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFail() {

    }
}
