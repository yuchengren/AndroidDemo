package com.ycr.kernel.image.glide.transform

import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.ycr.kernel.image.CornerType
import com.ycr.kernel.image.ImageDisplayType
import java.nio.ByteBuffer
import java.security.MessageDigest

/**
 * Glide图片转换器
 * created by yuchengren on 2019/4/30
 */
class GlideTransform(private val cornerRadius: Int,private val cornerType: Int = CornerType.ALL,
                     private var displayType: ImageDisplayType? = null): BitmapTransformation() {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
//        messageDigest.update(ByteBuffer.allocate(Integer.SIZE).array()) //此代码引发圆角失效
    }


    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
        val bitmap: Bitmap = when(displayType){
            ImageDisplayType.CENTER_CROP -> TransformationUtils.centerCrop(pool,toTransform,outWidth,outHeight)
            ImageDisplayType.CENTER_INSIDE -> TransformationUtils.centerInside(pool,toTransform,outWidth,outHeight)
            ImageDisplayType.FIT_CENTER -> TransformationUtils.fitCenter(pool,toTransform,outWidth,outHeight)
            ImageDisplayType.CIRCLE_CROP -> TransformationUtils.circleCrop(pool,toTransform,outWidth,outHeight)
            else  -> toTransform
        }
        return roundCrop(pool,bitmap)
    }

    private fun roundCrop(pool: BitmapPool, source: Bitmap): Bitmap{
        if(cornerRadius == 0){
            return source
        }
        val result = pool.get(source.width,source.height,Bitmap.Config.ARGB_8888)
        val canvas = Canvas(result)
        val paint = Paint().apply {
            shader = BitmapShader(source,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP)
            isAntiAlias = true
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN) //通过SRC_IN的模式取源图片和圆角矩形重叠的部分
        }
        val rectF = RectF(0f,0f,source.width.toFloat(),source.height.toFloat())
        canvas.drawRoundRect(rectF,cornerRadius.toFloat(),cornerRadius.toFloat(),paint)
        if(cornerType and CornerType.LEFT_TOP == 0){
            canvas.drawRect(0f,0f,cornerRadius.toFloat(),cornerRadius.toFloat(),paint)
        }
        if(cornerType and CornerType.RIGHT_TOP == 0){
            canvas.drawRect(rectF.right - cornerRadius,0f,rectF.right,cornerRadius.toFloat(),paint)
        }
        if(cornerType and CornerType.RIGHT_BOTTOM == 0){
            canvas.drawRect(rectF.right - cornerRadius,rectF.bottom - cornerRadius,rectF.right,rectF.bottom,paint)
        }
        if(cornerType and CornerType.LEFT_BOTTOM == 0){
            canvas.drawRect(0f,rectF.bottom - cornerRadius,cornerRadius.toFloat(),rectF.bottom,paint)
        }

        return result
    }
}