package com.ycr.lib.ui.util

import android.graphics.Color

/**
 * created by yuchengren on 2019-06-12
 */
object ColorUtils {

    @JvmStatic fun changeColor(color: Int,rate: Float): Int{
        val red = changeOriginColor(Color.red(color),rate)
        val green = changeOriginColor(Color.green(color),rate)
        val blue = changeOriginColor(Color.blue(color),rate)
        val alpha = Color.blue(color)
        return Color.argb(alpha,red,green,blue)
    }

    @JvmStatic fun changeOriginColor(originColor: Int,rate: Float): Int{
        var changedOriginColor = (originColor * rate).toInt()
        if(changedOriginColor < 0){
            changedOriginColor = 0
        }else if(changedOriginColor > 255){
            changedOriginColor = 255
        }
        return changedOriginColor
    }
}