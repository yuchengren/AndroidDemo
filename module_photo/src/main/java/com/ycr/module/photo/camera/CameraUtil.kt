package com.ycr.module.photo.camera

import android.app.Activity
import android.graphics.Rect
import android.hardware.Camera

/**
 * Created by yuchengren on 2019/1/22.
 */
object CameraUtil {

    private val FOCUS_AREA_SIZE = 500
    private val FOCUS_AREA_SIZE_HALF = FOCUS_AREA_SIZE / 2

    private val FOCUS_PARAM = 1000

    fun getRotation(activity: Activity,cameraId: Int): Int{
        val cameraInfo = Camera.CameraInfo()
        Camera.getCameraInfo(cameraId,cameraInfo)
        var windowRotation = activity.windowManager.defaultDisplay.rotation
        windowRotation = (windowRotation + 45) / 90 * 90
        return (cameraInfo.orientation + windowRotation) % 360
    }

    fun getPreviewSize(previewSizeList: MutableList<Camera.Size>,previewRatio: Float,minWidth: Int): Camera.Size{
        var size: Camera.Size? = null
        previewSizeList.sortBy { it.width }
        var index = 0
        for (item in previewSizeList) {
            if(item.width >= minWidth && equalsRate(item,previewRatio)){
                size = item
            }
        }

        if(size == null){
            size = getBestPreviewSize(previewSizeList,previewRatio)
        }
        return size
    }

    private fun equalsRate(size: Camera.Size,previewRatio: Float): Boolean{
        return Math.abs(size.width / size.height.toFloat() - previewRatio) < 0.2f
    }

    private fun getBestPreviewSize(previewSizeList: List<Camera.Size>,previewRatio: Float): Camera.Size{
        var previewRatioDiff = 100f
        var index = 0
        previewSizeList.forEachIndexed { i, size ->
            val ratio = size.width / size.height.toFloat()
            val diff = Math.abs(previewRatio - ratio)
            if(Math.abs(previewRatio - ratio) < previewRatioDiff){
                previewRatioDiff = diff
                index = i
            }
        }
        return previewSizeList[index]
    }

    fun getPictureSize(parameters: Camera.Parameters, previewSize: Camera.Size,minWidth: Int): Camera.Size{
        val pictureSizeList = parameters.supportedPictureSizes
        pictureSizeList.forEach {
            if(it == previewSize){
                return it
            }
        }
        return getPreviewSize(pictureSizeList,previewSize.width / previewSize.height.toFloat(),minWidth)
    }

    fun caculateFocusArea(width: Int,height: Int,x: Float,y: Float): Rect {
        val centerX = clamp( (x / width.toFloat() * FOCUS_PARAM * 2 - FOCUS_PARAM).toInt(), FOCUS_AREA_SIZE)
        val centerY = clamp( (y / height.toFloat() * FOCUS_PARAM * 2 - FOCUS_PARAM).toInt(), FOCUS_AREA_SIZE)
        return Rect(centerX - FOCUS_AREA_SIZE_HALF,centerY - FOCUS_AREA_SIZE_HALF,
                centerX + FOCUS_AREA_SIZE_HALF,centerY + FOCUS_AREA_SIZE_HALF)
    }

    private fun clamp(touchCoordinateInCameraReper: Int,focusAreaSize: Int): Int{
        return if(Math.abs(touchCoordinateInCameraReper) + focusAreaSize / 2 > FOCUS_PARAM){
            if(touchCoordinateInCameraReper > 0) 1000 - focusAreaSize / 2 else -1000 + focusAreaSize / 2
        }else{
            touchCoordinateInCameraReper
        }
    }

    fun handleZoom(isZoomIn: Boolean,parameters: Camera.Parameters){
        if(parameters.isZoomSupported){
            val maxZoom = parameters.maxZoom
            var zoom = parameters.zoom
            if(isZoomIn && zoom < maxZoom){
                zoom += 1
                parameters.zoom = zoom
            }else if(!isZoomIn && zoom > 0){
                zoom -= 1
                parameters.zoom = zoom
            }
        }
    }

}