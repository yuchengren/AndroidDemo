package com.ycr.kernel.image.glide

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.ycr.kernel.image.glide.transform.GlideTransform
import java.lang.Exception

/**
 * Glide图片加载器
 * created by yuchengren on 2019/4/29
 */
class GlideImageLoader(private val context: Context,defaultOpts: ImageDisplayOption?,private var glideDefaultOptions: RequestOptions? = null): IImageLoader {

    private val logger = BuildConfig.logger

    init {
        defaultOpts?.let { glideDefaultOptions = getGlideOptions(it) }
    }

    private fun getGlideOptions(opts: ImageDisplayOption?): RequestOptions{
        val options: RequestOptions = glideDefaultOptions?.let { RequestOptions().apply(it) }?: RequestOptions()
        opts?.run {
            defaultDrawable?.let { options.placeholder(it) }
            defaultDrawableId?.let { options.placeholder(it) }
            errorDrawable?.let { options.error(it) }
            errorDrawableId?.let { options.error(it) }

            options.skipMemoryCache(!cacheInMemory)
            options.diskCacheStrategy(if(cacheOnDisk) DiskCacheStrategy.RESOURCE else DiskCacheStrategy.NONE)

            options.transform(GlideTransform(cornerRadius,cornerType,imageDisplayType))

            if(maxWidth != 0 && maxHeight != 0){
                options.override(maxWidth,maxHeight)
            }
        }
        return options
    }

    override fun display(imageView: ImageView?, url: String?, opts: ImageDisplayOption?, listener: OnImageLoadListener?) {
        val requestBuilder = Glide.with(context).asBitmap().load(url).apply(getGlideOptions(opts)).listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                listener?.onFail(url, imageView, e)
                return false
            }

            override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                listener?.onSuccess(imageView, resource )
                return imageView == null
            }
        })

        if(imageView != null){
            requestBuilder.into(imageView)
        }else{
            requestBuilder.preload()
        }
    }

    override fun syncLoad(url: String?, opts: ImageDisplayOption?): Bitmap? {
        try {
            return Glide.with(context).asBitmap().apply(getGlideOptions(opts)).load(url).submit().get()
        }catch (e: Exception){
            logger.e("GlideImageLoader syncLoad:",e)
        }
        return null
    }

    override fun preLoad(url: String?, opts: ImageDisplayOption?, listener: OnImageLoadListener?) {
        display(null,url,opts,listener)
    }

    override fun clearCache(vararg cacheTypes: ImageCacheType) {
        for (cacheType in cacheTypes){
            when(cacheType){
                //清理磁盘缓存 需要在子线程中执行
                ImageCacheType.DISK -> Glide.get(context).clearDiskCache()
                //清理内存缓存 需要在UI线程中执行
                ImageCacheType.MEMORY -> Glide.get(context).clearMemory()
            }
        }
    }

    override fun pauseLoad() {
        Glide.with(context).pauseRequests()
    }

    override fun resumeLoad() {
        Glide.with(context).resumeRequests()
    }


}