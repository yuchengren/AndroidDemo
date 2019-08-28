package com.ycr.kernel.union.helper

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.support.v4.content.ContextCompat

/**
 * created by yuchengren on 2019/4/22
 */
@SuppressLint("StaticFieldLeak")
object ContextHelper {
    private lateinit var context: Context
    private lateinit var application: Application
    val resources: Resources
        get() = context.resources

    @JvmStatic fun doInit(application: Application) {
        this.application = application
        context = application.applicationContext
    }

    @JvmStatic fun getContext(): Context{
        return context
    }

    @JvmStatic fun getApplication(): Application{
        return application
    }

    @JvmStatic fun getString(resId: Int, vararg args: Any): String {
        return context.getString(resId, *args)
    }

    @JvmStatic fun getColor(resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }

    @JvmStatic fun getColorStateList(resId: Int): ColorStateList? {
        return ContextCompat.getColorStateList(context, resId)
    }

    @JvmStatic fun getDimenPixel(resId: Int): Int {
        return resources.getDimensionPixelSize(resId)
    }
}
