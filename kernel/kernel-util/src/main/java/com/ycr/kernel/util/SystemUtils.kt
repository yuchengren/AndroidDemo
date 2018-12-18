package com.ycr.kernel.util

import android.os.Build

/**
 * Created by yuchengren on 2018/12/18.
 */
object SystemUtils {
    fun getSystemVersion(): String{
        return Build.VERSION.RELEASE
    }

    fun getSystemModel(): String{
        return Build.MODEL
    }

    fun getSystemManufacturer(): String{
        return Build.MANUFACTURER
    }
}