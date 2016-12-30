package com.yuchengren.mvp.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.reflect.TypeToken;
import com.yuchengren.mvp.Bean.Student;
import com.yuchengren.mvp.R;
import com.yuchengren.mvp.Util.GsonUtil;
import com.yuchengren.mvp.Util.OkHttpUtil;
import com.yuchengren.mvp.factory.ModelFactory;
import com.yuchengren.mvp.factory.PresenterFactory;
import com.yuchengren.mvp.model.LoginModel;
import com.yuchengren.mvp.presenter.LoginPresenter;
import com.yuchengren.mvp.ui.activity.Base.BaseActivity;
import com.yuchengren.mvp.ui.adapter.Base.SuperAdapter;
import com.yuchengren.mvp.view.ILoginView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchengren on 2016/9/14.
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView, View.OnClickListener {

    private Button mBtnLogin;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mBtnLogin = (Button) findViewById(R.id.btn_login);
    }

    @Override
    protected void initData() {
        setPresenter(PresenterFactory.getInstance().getPresenter(LoginPresenter.class));
        mPresenter.setView(this);
        mPresenter.setModel(ModelFactory.getInstance().getModel(LoginModel.class));
    }

    @Override
    protected void initListener() {
        mBtnLogin.setOnClickListener(this);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                List<Student> studentList = new ArrayList<>();
                studentList.add(new Student("1","yu"));
                studentList.add(new Student("2","li"));
                String json = GsonUtil.formatObjectToJson(studentList);
                Log.e(TAG,"format json = "+json);
                List<Student> stuList = GsonUtil.parseJsonToList(json,Student.class);
                for (Student student : stuList) {
                    Log.e(TAG,"code="+student.getCode()+",name="+student.getName());
                }

                Log.e(TAG,"stuList="+stuList.toString());

                break;
            default:
                break;
        }
    }
}
