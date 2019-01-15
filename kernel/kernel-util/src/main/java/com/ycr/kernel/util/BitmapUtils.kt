package com.ycr.kernel.util

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory

/**
 * Created by yuchengren on 2019/1/4.
 */
fun Bitmap.getRoundedDrawable(context: Context,cornerRadius: Float): RoundedBitmapDrawable{
    return RoundedBitmapDrawableFactory.create(context.resources, this).apply {
        setAntiAlias(true)
        this.cornerRadius = cornerRadius
    }
}