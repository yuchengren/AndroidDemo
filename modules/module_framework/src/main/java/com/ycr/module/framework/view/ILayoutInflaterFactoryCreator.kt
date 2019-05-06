package com.ycr.module.framework.view

import android.support.v7.app.AppCompatDelegate
import android.view.LayoutInflater

/**
 * Created by ye on 2018/5/29.
 */
interface ILayoutInflaterFactoryCreator {
    /**
     * 最新的视图工厂创建
     *
     * @param appCompatDelegate
     * @return
     */
    fun createLayoutInflaterFactory2(appCompatDelegate: AppCompatDelegate): LayoutInflater.Factory2
}
