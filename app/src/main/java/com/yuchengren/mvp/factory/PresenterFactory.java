package com.yuchengren.mvp.factory;

import com.yuchengren.mvp.Util.ClassUtil;
import com.yuchengren.mvp.presenter.abs.Presenter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuchengren on 2016/9/18.
 */
public class PresenterFactory{

    public static PresenterFactory mPresenterFactory ;

    private Map<Class,Presenter> mPresenterMap;

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
        if(mPresenterMap == null){
            mPresenterMap = new HashMap<>();
        }
        P presenter = (P) mPresenterMap.get(pClass);
        if(presenter == null){
            presenter = ClassUtil.getInstance(pClass);
            mPresenterMap.put(pClass,presenter);
        }
        return presenter;
    }

}
