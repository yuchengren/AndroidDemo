package com.yuchengren.mvp.app.ui.view.imageedit

import android.graphics.Matrix
import android.graphics.RectF

/**
 * Created by yuchengren on 2018/11/16.
 */
object ImgEditHelper {

    fun getFitHomingValues(winRectF: RectF, imgRectF: RectF): ImgHomingValues {
        val imageRectF = RectF(imgRectF)
        val homingValues = ImgHomingValues()
        if (imageRectF.contains(winRectF)) {
            return homingValues
        }

        if (imageRectF.width() < winRectF.width() && imageRectF.height() < winRectF.height()) {
            homingValues.scale = Math.min(winRectF.width() / imageRectF.width(), winRectF.height() / imageRectF.height())
            Matrix().apply {
                setScale(homingValues.scale, homingValues.scale, imageRectF.centerX(), imageRectF.centerY())
            }.mapRect(imageRectF)
        }

        if (imageRectF.width() <= winRectF.width()) {
            homingValues.x += winRectF.centerX() - imageRectF.centerX()
        } else {
            if (imageRectF.left > winRectF.left) {
                homingValues.x += winRectF.left - imageRectF.left
            } else if (imageRectF.right < winRectF.right) {
                homingValues.x += winRectF.right - imageRectF.right
            }
        }

        if (imageRectF.height() <= winRectF.height()) {
            homingValues.y += winRectF.centerY() - imageRectF.centerY()
        } else {
            if(imageRectF.top > winRectF.top){
                homingValues.y += winRectF.top - imageRectF.top
            }else if(imageRectF.bottom < winRectF.bottom){
                homingValues.y += winRectF.bottom - imageRectF.bottom
            }
        }

        return homingValues
    }


}