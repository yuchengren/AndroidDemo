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

    override fun equals(other: Any?): Boolean {
        if(other == null){
            return false
        }
        if(other !is ImgHomingValues){
            return false
        }

        return this.x == other.x && this.y == other.y && this.scale == other.scale && this.rotate == other.rotate
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + scale.hashCode()
        result = 31 * result + rotate.hashCode()
        return result
    }
}