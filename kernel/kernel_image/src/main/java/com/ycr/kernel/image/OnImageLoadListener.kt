package com.ycr.kernel.image.glide

import android.graphics.Bitmap
import android.widget.ImageView

/**
 * 图片加载监听器
 * created by yuchengren on 2019/4/30
 */
interface OnImageLoadListener {
    /**
     * 加载成功
     */
    fun onSuccess(view: ImageView?, bitmap: Bitmap?)

    /**
     * 加载失败
     */
    fun onFail(url: String?,view: ImageView?,tr: Throwable?)

    /**
     * 加载进度更新
     */
    fun onProgress(current: Int,total: Int){}


}