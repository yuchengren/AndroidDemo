package com.yuchengren.mvp.app.ui.activity.Base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.yuchengren.mvp.R;
import com.yuchengren.mvp.constant.SharePrefsKey;
import com.yuchengren.mvp.constant.SharePrefsValue;
import com.yuchengren.mvp.util.OkHttpUtil;
import com.yuchengren.mvp.cache.UiStack;
import com.yuchengren.mvp.app.model.abs.Model;
import com.yuchengren.mvp.app.presenter.abs.Presenter;
import com.yuchengren.mvp.app.view.IView;
import com.yuchengren.mvp.util.SharePrefsUtil;

import okhttp3.Call;


/**
 * Created by yuchengren on 2017/2/14.
 */

public abstract class SuperActivity<P extends Presenter> extends AppCompatActivity {

    protected String TAG = this.getClass().getName();
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar();
        initTheme();
        setContentView(getLayoutResID());
        init();
        initViews();
        initListeners();
        initData();
        Log.i("className=",getClass().getName());
    }

    protected  void initTheme(){
        String theme_style = SharePrefsUtil.getInstance().getString(SharePrefsKey.THEME_STYLE,SharePrefsValue.Theme.RED);
        if(SharePrefsValue.Theme.BLUE.equals(theme_style)){
            setTheme(R.style.BlueTheme);
        }else{
            setTheme(R.style.RedTheme);
        }
    }

    protected  void showStatusBar(){

    }
    protected abstract int getLayoutResID() ;

    protected abstract void initViews() ;

    protected abstract void initListeners() ;

    protected abstract void initData() ;

    protected  void init(){
        UiStack.getInstance().addActivity(this);
    }

    protected <M extends Model,V extends IView> void initPresenter(V view, P presenter, M model){
        this.mPresenter = presenter;
        if(mPresenter != null){
            mPresenter.onAttach(this);
            mPresenter.setView(view);
            mPresenter.setModel(model);
            mPresenter.setIntent(getIntent());
            mPresenter.onCreate();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(mPresenter != null){
            mPresenter.onRestart();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mPresenter != null){
            mPresenter.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mPresenter != null){
            mPresenter.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mPresenter != null){
            mPresenter.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mPresenter != null){
            mPresenter.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        if(mPresenter != null){
            mPresenter.onDestroy();
            mPresenter.onDetach();
        }
        UiStack.getInstance().removeActivity(this);
        super.onDestroy();
    }

    @Override
    public void finish() {
        cancelOkHttpCalls();
        super.finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(mPresenter != null){
            mPresenter.onNewIntent(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * 在Activity销毁前，根据Context对象Tag,停止持有该Activity的Context引用的网络访问
     */
    public void cancelOkHttpCalls(){
        for (Call call : OkHttpUtil.getInstance().getQueuedAndRunningCallList()) {
            if(call.request().tag() == this){
                call.cancel();
            }
        }
    }
}
