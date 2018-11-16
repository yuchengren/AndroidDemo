package com.yuchengren.mvp.app.ui.view.imageedit

/**
 * Created by yuchengren on 2018/11/16.
 */
class ImgHomingValues(var x: Float = 0f,var y: Float = 0f,var scale: Float = 1f,var rotate: Float = 0f){

    fun scrollConcat(homingValues: ImgHomingValues){
        x -= homingValues.x
        y -= homingValues.y
        scale *= homingValues.scale
    }

}