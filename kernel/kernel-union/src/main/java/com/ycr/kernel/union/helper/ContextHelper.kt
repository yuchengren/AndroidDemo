package com.ycr.kernel.union.helper

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.support.v4.content.ContextCompat

/**
 * Created by yuchengren on 2018/12/7.
 */
object ContextHelper {
    lateinit var context: Context

    fun doInit(application: Application){
        context = application.applicationContext
    }

    fun getResources(): Resources{
        return context.resources
    }

    fun getString(resId: Int,vararg args: Any): String{
        return context.getString(resId,args)
    }

    fun getColor(resId: Int): Int{
        return ContextCompat.getColor(context,resId)
    }

    fun getDimenPixel(resId: Int): Int{
        return context.resources.getDimensionPixelSize(resId)
    }

}