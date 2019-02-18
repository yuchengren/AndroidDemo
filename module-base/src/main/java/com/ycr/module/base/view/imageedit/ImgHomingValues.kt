package com.ycr.module.base.view.imageedit

/**
 * Created by yuchengren on 2018/11/16.
 */
class ImgHomingValues(var x: Float = 0f,var y: Float = 0f,var scale: Float = 1f,var rotate: Float = 0f){

    fun concat(homingValues: ImgHomingValues){
        x -= homingValues.x
        y -= homingValues.y
        scale *= homingValues.scale
    }

}