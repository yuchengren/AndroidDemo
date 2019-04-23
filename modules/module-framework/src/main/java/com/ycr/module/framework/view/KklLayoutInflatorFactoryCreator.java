package com.ycr.module.framework.view;

import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;


/**
 * Created by ye on 2018/5/29.
 */
public class KklLayoutInflatorFactoryCreator implements ILayoutInflaterFactoryCreator {

    @Override
    public LayoutInflater.Factory2 createLayoutInflaterFactory2(AppCompatDelegate appCompatDelegate) {
        return new KklLayoutInflator(appCompatDelegate);
    }
}
