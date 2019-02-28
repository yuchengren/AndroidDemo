package com.ycr.module.photo.constants

/**
 * Created by yuchengren on 2019/1/25.
 */
class IConstants{
    interface Entrances{
        companion object {
            const val PHOTO_BOOK = "photoBook" //相册
            const val PHOTO_CAMERA = "photoCamera" //拍照
            const val PHOTO_CLIP = "photoClip" //裁剪
        }
    }

    /**
     * 图片来源
     */
    interface PicSource{
        companion object {
            const val PHOTO_BOOK = "photoBook" //相册页面
            const val PHOTO_CAMERA = "photoCamera" //拍照
            const val OUTER = "outer" //外界传入
        }
    }

}