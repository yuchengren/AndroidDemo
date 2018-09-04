package com.ycr.lib.changeskin.attr

import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.ycr.lib.changeskin.ResourceManager
import com.ycr.lib.changeskin.SkinManager
import com.ycr.lib.changeskin.constants.SkinConfig

/**
 * Created by yuchengren on 2018/8/29.
 */
enum class SkinAttrType(val attrType: String) {
    BACKGROUND(SkinConfig.ATTRTYPE_BACKGROUND){
        override fun apply(view: View, resName: String) {
            val drawable = getResourceManager().getDrawable(resName)?.run { view.background = this }
            if(drawable != null){
                return
            }
            try {
                getResourceManager().getColor(resName).let { view.setBackgroundColor(it) }
            }catch (e: Resources.NotFoundException){
                e.printStackTrace()
            }
        }
    },
    SRC(SkinConfig.ATTRTYPE_SRC){
        override fun apply(view: View, resName: String) {
            if(view is ImageView){
                view.setImageDrawable(getResourceManager().getDrawable(resName)?:return)
            }
        }

    },
    TEXTCOLOR(SkinConfig.ATTRTYPE_TEXTCOLOR){
        override fun apply(view: View, resName: String) {
            if (view is TextView){
                view.setTextColor(getResourceManager().getColorStateList(resName)?:return)
            }

        }
    },
    DIVIDER(SkinConfig.ATTRTYPE_DIVIDER){
        override fun apply(view: View, resName: String) {
            if(view is ListView){
                view.divider = getResourceManager().getDrawable(resName)?:return
            }
        }

    }

    ;

    abstract fun apply(view: View, resName: String)

    fun getResourceManager(): ResourceManager{
        return SkinManager.getResourceManager()
    }
}