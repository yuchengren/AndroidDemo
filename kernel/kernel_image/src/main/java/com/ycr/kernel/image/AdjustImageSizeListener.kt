package com.ycr.kernel.image

import android.graphics.Bitmap
import android.widget.ImageView
import com.ycr.kernel.image.glide.OnImageLoadListener

/**
 * 根据宽度自适应高度
 * created by yuchengren on 2019/4/30
 */
class AdjustImageSizeListener(private val width: Int): OnImageLoadListener {

    override fun onSuccess(view: ImageView?, bitmap: Bitmap?) {
        view?.run {
            val lp = layoutParams
            if(bitmap != null && bitmap.width != 0 && width != 0){
                lp.height = width * bitmap.height / bitmap.width
                layoutParams = lp
            }
        }
    }

    override fun onFail(url: String?, view: ImageView?, tr: Throwable?) {}
}