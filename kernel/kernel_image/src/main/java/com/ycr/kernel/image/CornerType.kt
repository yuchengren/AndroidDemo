package com.ycr.kernel.image

/**
 * 圆角类型
 * created by yuchengren on 2019/4/30
 */
class CornerType{
    companion object{
        /**
         * 左上圆角
         */
        const val LEFT_TOP = 0b1
        /**
         * 右上圆角
         */
        const val RIGHT_TOP = 0b10
        /**
         * 右下圆角
         */
        const val RIGHT_BOTTOM = 0b100
        /**
         * 左下圆角
         */
        const val LEFT_BOTTOM = 0b1000

        /**
         * 四个圆角
         */
        const val ALL = 0b1111
    }
}