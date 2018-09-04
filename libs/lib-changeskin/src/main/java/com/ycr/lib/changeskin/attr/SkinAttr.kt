package com.ycr.lib.changeskin.attr

import android.view.View

/**
 * Created by yuchengren on 2018/8/29.
 */
class SkinAttr(val attrType: SkinAttrType,val resName: String){
    fun apply(view: View){
        attrType.apply(view,resName)
    }
}