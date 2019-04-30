package com.ycr.kernel.image.glide

import android.graphics.Bitmap
import android.widget.ImageView

/**
 * 图片加载器接口
 * created by yuchengren on 2019/4/30
 */
interface IImageLoader {
    /**
     * 渲染图片
     */
    fun display(view: ImageView?, url: String?, opts: ImageDisplayOption?, listener: OnImageLoadListener?)

    /**
     * 同步加载图片Url
     */
    fun syncLoad(url: String?,opts: ImageDisplayOption?): Bitmap?

    /**
     * 预加载
     */
    fun preLoad(url: String?, opts: ImageDisplayOption?, listener: OnImageLoadListener?)

    /**
     * 清理缓存
     */
    fun clearCache(vararg cacheTypes: ImageCacheType)

    /**
     * 暂停加载
     */
    fun pauseLoad()

    /**
     * 暂停后重新启动加载
     */
    fun resumeLoad()
}