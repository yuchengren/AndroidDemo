package com.ycr.kernel.util

import android.graphics.drawable.Drawable
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat



/**
 * Created by yuchengren on 2019/1/25.
 */
fun Drawable.drawableToBitmap(): Bitmap{
    val width = intrinsicWidth// 取drawable的长宽
    val height = intrinsicHeight
    val config = if (getOpacity() !== PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565// 取drawable的颜色格式
    val bitmap = Bitmap.createBitmap(width, height, config)// 建立对应bitmap
    val canvas = Canvas(bitmap)// 建立对应bitmap的画布
    setBounds(0, 0, width, height)
    draw(canvas)// 把drawable内容画到画布中
    return bitmap
}