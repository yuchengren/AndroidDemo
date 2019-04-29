package com.ycr.kernel.util

import android.os.Build

/**
 * Created by yuchengren on 2018/12/18.
 */
object SystemUtils {
    @JvmStatic
    fun getSystemVersion(): String{
        return Build.VERSION.RELEASE
    }

    @JvmStatic
    fun getSystemModel(): String{
        return Build.MODEL
    }

    @JvmStatic
    fun getSystemManufacturer(): String{
        return Build.MANUFACTURER
    }
}