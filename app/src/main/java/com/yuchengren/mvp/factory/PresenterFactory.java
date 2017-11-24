package com.yuchengren.mvp.factory;

import com.yuchengren.mvp.util.ClassUtil;
import com.yuchengren.mvp.presenter.abs.Presenter;

/**
 * Created by yuchengren on 2016/9/18.
 */
public class PresenterFactory{

    public static PresenterFactory mPresenterFactory ;


    private PresenterFactory() {
    }

    public static PresenterFactory getInstance(){
        if(mPresenterFactory == null){
            synchronized (PresenterFactory.class){
                if(mPresenterFactory == null){
                    mPresenterFactory = new PresenterFactory();
                }
            }
        }
        return mPresenterFactory;
    }

    public <P extends Presenter> P getPresenter(Class<P> pClass){
        P presenter = ClassUtil.getInstance(pClass);
        return presenter;
    }

}
