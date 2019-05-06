package com.ycr.module.base.constant

import com.ycr.kernel.image.CornerType
import com.ycr.kernel.image.ImageDisplayType
import com.ycr.kernel.image.glide.ImageDisplayOption
import com.ycr.kernel.union.helper.ContextHelper
import com.ycr.module.base.R

/**
 * created by yuchengren on 2019/5/5
 */
class ImageOptions {

    companion object{
        val default = ImageDisplayOption.build().
                defaultDrawableId(R.mipmap.ic_launcher).
                errorDrawableId(R.mipmap.ic_launcher).
                cornerType(CornerType.ALL).
                imageDisplayType(ImageDisplayType.CENTER_CROP)

        val defaultNoHolder = ImageDisplayOption.build().
                cornerType(CornerType.ALL).
                imageDisplayType(ImageDisplayType.CENTER_CROP)

        val cornerNoHolder = ImageDisplayOption.build().
                cornerType(CornerType.ALL).
                imageDisplayType(ImageDisplayType.CENTER_CROP).
                cornerRadius(ContextHelper.getDimenPixel(R.dimen.corners_radius_image))
    }
}