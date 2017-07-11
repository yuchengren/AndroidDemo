package com.yuchengren.mvp.presenter.interfaces;

import android.content.Context;
import android.content.Intent;

/**
 * Presenter接口，仿Fragment的生命周期
 * Created by yuchengren on 2016/9/14.
 */
public interface IPresenter {
    void onAttach(Context context);
    void onCreate();
    void onRestart();
    void onStart();
    void onResume();
    void onPause();
    void onStop();
    void onDestroy();
    void onDetach();
    void onNewIntent(Intent intent);
}
