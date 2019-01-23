package com.ycr.module.base.view.imageedit

import android.animation.TypeEvaluator

/**
 * Created by yuchengren on 2018/11/16.
 */
class ImgHomingEvaluator: TypeEvaluator<ImgHomingValues> {

    override fun evaluate(fraction: Float, startValue: ImgHomingValues, endValue: ImgHomingValues): ImgHomingValues {
        val x = startValue.x + fraction * (endValue.x - startValue.x)
        val y = startValue.y + fraction * (endValue.y - startValue.y)
        val scale = startValue.scale + fraction * (endValue.scale - startValue.scale)
        val rotate = startValue.rotate + fraction * (endValue.rotate - startValue.rotate)
        return ImgHomingValues(x, y, scale, rotate)
    }
}