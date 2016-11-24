package com.yuchengren.mvp.presenter.abs;

import android.content.Context;

import com.yuchengren.mvp.presenter.interfaces.IPresenter;

/**
 * Created by yuchengren on 2016/9/18.
 */
public abstract class Presenter<V,M>  implements IPresenter {

    protected Context mContext;
    protected V mView;
    protected M mModel;


    @Override
    public void onAttach(Context context) {
        this.mContext = context;
    }

    @Override
    public void onDetach() {
        mContext = null;
        mView = null;
        mModel = null;
        recycle();
    }

    public void setView(V view){
        this.mView = view;
    }

    public void setModle(M modle){
        this.mModel = modle;
    }

    protected  abstract void recycle();
}
