package com.ycr.kernel.util

import android.content.Context

/**
 * Created by yuchengren on 2018/12/18.
 */

fun Context.getPhonePixels(concatChar: Char = Chars.ASTERISK): String{
    val width = resources.displayMetrics?.widthPixels ?: 0
    val height = resources.displayMetrics?.heightPixels ?: 0
    return width.toString() + concatChar + height.toString()
}