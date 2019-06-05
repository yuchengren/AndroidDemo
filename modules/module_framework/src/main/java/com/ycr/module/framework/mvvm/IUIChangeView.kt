package com.ycr.module.framework.mvvm

import android.os.Bundle

/**
 * created by yuchengren on 2019-05-31
 */
interface IUIChangeView {

    companion object{
        const val CLAZZ = "Class"
        const val BUNDLE = "Bundle"
        const val CANONICAL_NAME = "canonicalName"
    }

    fun showLoading()

    fun showLoading(msg: String? = "")

    fun dismissLoading()

    fun startActivity(cls: Class<*>)

    fun startActivity(cls: Class<*>,bundle: Bundle?)

    fun finish()
}