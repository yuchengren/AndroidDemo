package com.yuchengren.mvp.presenter.abs;

import android.content.Context;
import android.content.Intent;

import com.yuchengren.mvp.model.abs.Model;
import com.yuchengren.mvp.presenter.interfaces.IPresenter;
import com.yuchengren.mvp.view.IView;

/**
 * Created by yuchengren on 2016/9/18.
 */
public abstract class Presenter<V extends IView,M extends Model>  implements IPresenter {

    protected String TAG = this.getClass().getSimpleName();
    protected Context mContext;
    protected V mView;
    protected M mModel;
    protected Intent mIntent;

    /**
     * 当为Activity指定Presenter时，为Presenter注入该Activity的Context引用
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

	/**
	 * 可以做Presenter自己的回收资源的操作
     */
    @Override
    public void onDestroy() {

    }

    /**
     * 当指定了Presenter的Activity，再回调onFinish()方法时，
     * 释放Presenter对Activity对象的引用，防止Activity对象无法销毁，导致内存泄露
     */
    @Override
    public void onDetach() {
        mContext = null;
        mView = null;
        mModel = null;
        mIntent = null;
    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    public void setView(V view){
        this.mView = view;
    }

    public void setModel(M model){
        this.mModel = model;
    }

    public Intent getIntent() {
        return mIntent;
    }

    public void setIntent(Intent intent) {
        this.mIntent = intent;
    }

}
