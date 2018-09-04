package com.ycr.lib.changeskin

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.text.TextUtils
import com.ycr.lib.changeskin.constants.SkinConfig

/**
 * Created by yuchengren on 2018/8/28.
 */
class ResourceManager(private val resources: Resources, private val pluginPkg: String?, private var pluginSuffix: String?) {
    init {
        pluginSuffix = pluginSuffix?:""
    }

    fun getDrawable(name: String): Drawable?{
        try {
            val name = appendNameSuffix(name)
            return resources.getDrawable(resources.getIdentifier(name,SkinConfig.DEFTYPE_DRAWABLE,pluginPkg))
        }catch (e: Resources.NotFoundException){
            e.printStackTrace()
        }
        return null
    }

    @Throws(Resources.NotFoundException::class)
    fun getColor(name: String): Int{
        val name = appendNameSuffix(name)
        return resources.getColor(resources.getIdentifier(name,SkinConfig.DEFTYPE_COLOR,pluginPkg))
    }

    fun getColorStateList(name: String): ColorStateList?{
        try {
            val name = appendNameSuffix(name)
            return resources.getColorStateList(resources.getIdentifier(name,SkinConfig.DEFTYPE_COLOR,pluginPkg))
        }catch (e: Resources.NotFoundException){
            e.printStackTrace()
        }
        return null
    }

    private fun appendNameSuffix(name: String): String{
        return name.plus(if(TextUtils.isEmpty(pluginSuffix))"" else "_".plus(pluginSuffix))
    }

}