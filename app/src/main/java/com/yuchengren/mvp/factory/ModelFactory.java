package com.yuchengren.mvp.factory;


import com.yuchengren.mvp.util.ClassUtil;
import com.yuchengren.mvp.model.abs.Model;

/**
 * Created by yuchengren on 2016/9/18.
 */
public class ModelFactory {

    public static ModelFactory mModelFactory ;

    private ModelFactory() {
    }

    public static ModelFactory getInstance(){

        if(mModelFactory == null){

            synchronized (ModelFactory.class){

                if(mModelFactory == null){

                    mModelFactory = new ModelFactory();
                }
            }
        }
        return mModelFactory;
    }

    public <M extends Model> M getModel(Class<M> pClass){
        M model = ClassUtil.getInstance(pClass);
        return model;
    }
    
}
