package com.ycr.lib.changeskin.utils

import android.content.Context
import com.ycr.lib.changeskin.constants.SkinConfig

/**
 * Created by yuchengren on 2018/8/28.
 */
object SkinPrefsHelper {

    fun getPluginPath(context: Context): String?{
        val sp = context.getSharedPreferences(SkinConfig.PREF_NAME, Context.MODE_PRIVATE)
        return sp.getString(SkinConfig.KEY_PLUGIN_PATH,"")
    }

    fun getPluginPkg(context: Context): String?{
        val sp = context.getSharedPreferences(SkinConfig.PREF_NAME, Context.MODE_PRIVATE)
        return sp.getString(SkinConfig.KEY_PLUGIN_PKG,"")
    }

    fun getPluginSuffix(context: Context): String?{
        val sp = context.getSharedPreferences(SkinConfig.PREF_NAME, Context.MODE_PRIVATE)
        return sp.getString(SkinConfig.KEY_PLUGIN_SUFFIX,"")
    }

    fun putPluginPath(context: Context, path: String?){
        val sp = context.getSharedPreferences(SkinConfig.PREF_NAME, Context.MODE_PRIVATE)
        sp.edit().putString(SkinConfig.KEY_PLUGIN_PATH,path).apply()
    }

    fun putPluginPkg(context: Context, pkg: String?){
        val sp = context.getSharedPreferences(SkinConfig.PREF_NAME, Context.MODE_PRIVATE)
        sp.edit().putString(SkinConfig.KEY_PLUGIN_PKG,pkg).apply()
    }

    fun putPluginSuffix(context: Context, suffix: String?){
        val sp = context.getSharedPreferences(SkinConfig.PREF_NAME, Context.MODE_PRIVATE)
        sp.edit().putString(SkinConfig.KEY_PLUGIN_SUFFIX,suffix).apply()
    }

    fun clear(context: Context){
        val sp = context.getSharedPreferences(SkinConfig.PREF_NAME, Context.MODE_PRIVATE)
        sp.edit().clear().commit()
    }


}