package com.ycr.kernel.image.glide

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.*
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.ycr.kernel.image.CornerType
import com.ycr.kernel.image.ImageDisplayType
import com.ycr.kernel.image.glide.transform.GlideCornerTransform
import com.ycr.kernel.image.util.ImageUtils

/**
 * Glide图片加载器
 * created by yuchengren on 2019/4/29
 */
class GlideImageLoader(private val context: Context,private val defaultOpts: ImageDisplayOption?,private var glideDefaultOptions: RequestOptions? = null): IImageLoader {

    private val logger = BuildConfig.logger

    init {
        defaultOpts?.let { glideDefaultOptions = getGlideOptions(it) }
    }

    @SuppressLint("CheckResult")
    private fun getGlideOptions(opts: ImageDisplayOption?): RequestOptions{
        val options: RequestOptions = glideDefaultOptions?.let { RequestOptions().apply(it) }?: RequestOptions()

        opts?.run {
            defaultDrawable?.let { options.placeholder(it) }
            defaultDrawableId?.let { options.placeholder(it) }
            errorDrawable?.let { options.error(it) }
            errorDrawableId?.let { options.error(it) }

            cacheInMemory?.let { options.skipMemoryCache(!it) }
            cacheOnDisk?.let { options.diskCacheStrategy(if(it) DiskCacheStrategy.RESOURCE else DiskCacheStrategy.NONE) }

            if(maxWidth != 0 && maxHeight != 0){
                options.override(maxWidth,maxHeight)
            }
        }

        val displayType = opts?.imageDisplayType?:(defaultOpts?.imageDisplayType)

        val cornerRadius = opts?.cornerRadius?:(defaultOpts?.cornerRadius)

        val scaleTypeTransform = getScaleTypeTransform(displayType)

        if(cornerRadius == null || cornerRadius == 0){
            scaleTypeTransform?.let { options.transform(it) }
        }else{
            if(scaleTypeTransform == null){
                options.transform(GlideCornerTransform(cornerRadius, (opts?.cornerType?:(defaultOpts?.cornerType))?:CornerType.ALL))
            }else{
                options.transforms(scaleTypeTransform,GlideCornerTransform(cornerRadius, (opts?.cornerType?:(defaultOpts?.cornerType))?:CornerType.ALL))
            }
        }
        return options
    }

    private fun getScaleTypeTransform(imageDisplayType: ImageDisplayType?): Transformation<Bitmap>?{
        return when(imageDisplayType){
            ImageDisplayType.CENTER_CROP -> CenterCrop()
            ImageDisplayType.CENTER_INSIDE -> CenterInside()
            ImageDisplayType.FIT_CENTER -> FitCenter()
            ImageDisplayType.CIRCLE_CROP -> CircleCrop()
            else -> null
        }
    }

    override fun display(view: ImageView?, url: String?, opts: ImageDisplayOption?, listener: OnImageLoadListener?) {
        val requestBuilder = Glide.with(context).load(url).apply(getGlideOptions(opts)).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                listener?.onFail(url, view, e)
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                listener?.onSuccess(view, resource?.let { ImageUtils.drawableToBitmap(it) } )
                return view == null
            }
        })

        if(view != null){
            requestBuilder.into(view)
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