package com.yuchengren.mvp.app;

import android.app.Application;

/**
 * Created by yuchengren on 2016/9/2.
 */
public class MvpApplication extends Application {

    private static Application mMvpApplication;

    public synchronized static Application getInstance(){
        if(mMvpApplication == null ){
            mMvpApplication = new MvpApplication();
        }
        return mMvpApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }


}
