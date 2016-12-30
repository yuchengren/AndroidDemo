package com.yuchengren.mvp.ui.activity.Base;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.yuchengren.mvp.cache.UiStack;
import com.yuchengren.mvp.presenter.abs.Presenter;

/**
 * Created by yuchengren on 2016/9/2.
 */
public abstract class BaseActivity<P extends Presenter> extends Activity {

    protected  String TAG = this.getClass().getSimpleName();

    protected P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        init();
        initView();
        initData();
        initListener();
    }

    private void init() {
        UiStack.getInstance().addActivity(this);
    }


    protected void setPresenter(P presenter){
        this.mPresenter = presenter;
        mPresenter.onAttach(this);
    }

    protected abstract int getLayoutResID() ;

    protected abstract void initView() ;

    protected abstract void initListener() ;

    protected abstract void initData() ;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.onDetach();
        }
        UiStack.getInstance().removeActivity(this);
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


}
