package com.ycr.kernel.union.helper

import android.graphics.Bitmap
import android.widget.ImageView
import com.ycr.kernel.image.AdjustImageSizeListener
import com.ycr.kernel.image.glide.IImageLoader
import com.ycr.kernel.image.glide.ImageCacheType
import com.ycr.kernel.image.glide.ImageDisplayOption
import com.ycr.kernel.image.glide.OnImageLoadListener

/**
 * 图片加载工具类
 * created by yuchengren on 2019/4/30
 */
object ImageHelper {
    private val loader: IImageLoader = UnionContainer.imageLoader

    @JvmStatic fun display(imageView: ImageView, url: String?, opts: ImageDisplayOption?, listener: OnImageLoadListener?){
        loader.display(imageView,url,opts,listener)
    }

    @JvmStatic fun display(imageView: ImageView, url: String?){
        display(imageView,url,null,null)
    }

    @JvmStatic fun display(imageView: ImageView, url: String?,opts: ImageDisplayOption?){
        display(imageView,url,opts,null)
    }

    @JvmStatic fun display(imageView: ImageView, url: String?,listener: OnImageLoadListener?){
        display(imageView,url,null,listener)
    }

    @JvmStatic fun displayAdjust(imageView: ImageView, url: String?, width: Int){
        display(imageView,url,null,AdjustImageSizeListener(width))
    }

    @JvmStatic fun syncLoad(url: String?, opts: ImageDisplayOption? = null): Bitmap?{
        return loader.syncLoad(url,opts)
    }

    @JvmStatic fun preLoad(url: String?, opts: ImageDisplayOption?, listener: OnImageLoadListener?){
        loader.preLoad(url,opts,listener)
    }

    @JvmStatic fun preLoad(url: String?, listener: OnImageLoadListener?){
        loader.preLoad(url,null,listener)
    }

    @JvmStatic fun clearCache(vararg cacheTypes: ImageCacheType){
        loader.clearCache(*cacheTypes)
    }

    @JvmStatic fun pauseLoad(){
        loader.pauseLoad()
    }

    @JvmStatic fun resumeLoad(){
        loader.resumeLoad()
    }

}