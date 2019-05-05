package com.ycr.kernel.image.glide

import android.graphics.drawable.Drawable
import com.ycr.kernel.image.CornerType
import com.ycr.kernel.image.ImageDisplayType

/**
 * 图片显示配置
 * created by yuchengren on 2019/4/30
 */
class ImageDisplayOption private constructor(){
    var defaultDrawable: Drawable? = null //默认展位图
    var defaultDrawableId: Int? = null
    var errorDrawable: Drawable? = null//加载错误显示的图片
    var errorDrawableId: Int? = null

    var cacheInMemory: Boolean? = null //是否启动内存缓存
    var cacheOnDisk: Boolean? = null //是否启动磁盘缓存

    var cornerRadius: Int? = null //圆角尺寸 px
    var cornerType: CornerType? = null
    var imageDisplayType: ImageDisplayType? = null

    var maxWidth: Int = 0
    var maxHeight:Int = 0

    fun defaultDrawable(defaultDrawable: Drawable): ImageDisplayOption{
        this.defaultDrawable = defaultDrawable
        return this
    }

    fun defaultDrawableId(defaultDrawableId: Int): ImageDisplayOption{
        this.defaultDrawableId = defaultDrawableId
        return this
    }

    fun errorDrawable(errorDrawable: Drawable): ImageDisplayOption{
        this.errorDrawable = errorDrawable
        return this
    }

    fun errorDrawableId(errorDrawableId: Int): ImageDisplayOption{
        this.errorDrawableId = errorDrawableId
        return this
    }

    fun cacheInMemory(cacheInMemory: Boolean): ImageDisplayOption{
        this.cacheInMemory = cacheInMemory
        return this
    }

    fun cacheOnDisk(cacheOnDisk: Boolean): ImageDisplayOption{
        this.cacheOnDisk = cacheOnDisk
        return this
    }
    fun cornerRadius(cornerRadius: Int): ImageDisplayOption{
        this.cornerRadius = cornerRadius
        return this
    }
    fun cornerType(cornerType: CornerType): ImageDisplayOption{
        this.cornerType = cornerType
        return this
    }
    fun imageDisplayType(imageDisplayType: ImageDisplayType): ImageDisplayOption{
        this.imageDisplayType = imageDisplayType
        return this
    }

    fun maxWidth(maxWidth: Int): ImageDisplayOption{
        this.maxWidth = maxWidth
        return this
    }
    fun maxHeight(maxHeight: Int): ImageDisplayOption{
        this.maxHeight = maxHeight
        return this
    }

    companion object{
        @JvmStatic
        fun build(): ImageDisplayOption{
            return ImageDisplayOption()
        }
    }

}