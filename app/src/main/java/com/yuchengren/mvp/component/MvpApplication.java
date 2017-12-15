package com.yuchengren.mvp.component;

import android.app.Application;

import com.yuchengren.mvp.util.OkHttpUtil;

import okhttp3.OkHttpClient;

/**
 * Created by yuchengren on 2016/9/2.
 */
public class MvpApplication extends Application {

    private static MvpApplication mMvpApplication;
    /**
     * 默认初始化的OkHttpClient
     */
    private OkHttpClient mDefaultOkHttpClient;

    public synchronized static MvpApplication getInstance(){
        if(mMvpApplication == null ){
            mMvpApplication = new MvpApplication();
        }
        return mMvpApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mMvpApplication = this;
    }

    /**
     * 初始化OkHttpClient
     */
    private void initOkHttp() {
        OkHttpUtil.getInstance().initClient();
        setDefaultOkHttpClient(OkHttpUtil.getInstance().getOkHttpClient());
    }

    public OkHttpClient getDefaultOkHttpClient() {
        if(mDefaultOkHttpClient == null){
            initOkHttp();
        }
        return mDefaultOkHttpClient;
    }

    public void setDefaultOkHttpClient(OkHttpClient mDefaultOkHttpClient) {
        this.mDefaultOkHttpClient = mDefaultOkHttpClient;
    }


}
