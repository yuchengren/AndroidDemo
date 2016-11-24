package com.yuchengren.mvp.factory;

import com.yuchengren.mvp.Util.ClassUtil;
import com.yuchengren.mvp.model.abs.Model;
import com.yuchengren.mvp.model.interfaces.IModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuchengren on 2016/9/18.
 */
public class ModelFactory {
    public static ModelFactory mModelFactory ;

    private Map<Class,IModel> mModelMap;

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
        if(mModelMap == null){
            mModelMap = new HashMap<>();
        }
        M model = (M) mModelMap.get(pClass);
        if(model == null){
            model = ClassUtil.getInstance(pClass);
            mModelMap.put(pClass,model);
        }
        return model;
    }
    
}
