package com.yuchengren.demo.view.imageedit

import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator

/**
 * Created by yuchengren on 2018/11/16.
 */
class ImgHomingAnimator: ValueAnimator() {

    private var evaluator: ImgHomingEvaluator? = null

    init {
        interpolator = AccelerateDecelerateInterpolator()
    }

    fun setHomingValues(startHomingValues: ImgHomingValues, endHomingValues: ImgHomingValues){
        super.setObjectValues(startHomingValues,endHomingValues)
        if(evaluator == null){
            evaluator = ImgHomingEvaluator()
        }
        setEvaluator(evaluator)
    }
}