package com.ycr.lib.changeskin.attr

import android.view.View

/**
 * Created by yuchengren on 2018/8/29.
 */
class SkinView(private val view: View?,private val skinAttrs: List<SkinAttr>){

    fun apply(){
        view?.let {
            skinAttrs.forEach { it.apply(it@view) }
        }
    }
}