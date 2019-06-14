package com.ycr.lib.ui.util

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.view.View

/**
 * created by yuchengren on 2019-06-13
 */
class ShapeBuilder {

    companion object{
        const val RECTANGLE = 0
        const val OVAL = 1
        const val LINE = 2
        const val RING = 3

        const val LINEAR_GRADIENT = 0
        const val RADIAL_GRADIENT = 1
        const val SWEEP_GRADIENT = 2
    }

    val state_disabled = intArrayOf(-android.R.attr.state_enabled)
    val state_pressed = intArrayOf(android.R.attr.state_pressed,android.R.attr.state_enabled)
    val state_selected = intArrayOf(android.R.attr.state_selected)
    val state_none = intArrayOf()


    var cornerRadius: Float = 0f
    var topLeftRadius: Float = 0f
    var topRightRadius: Float = 0f
    var bottomRightRadius: Float = 0f
    var bottomLeftRadius: Float = 0f

    var strokeWidth: Int = 0
    var strokeColor: Int = 0
    var strokeDashWidth: Int = 0
    var strokeDashGap: Int = 0

    var pressedStrokeVisible: Boolean = true
    var disabledStrokeVisible: Boolean = true
    var selectedStrokeVisible: Boolean = true

    var pressedStrokeColor: Int = 0
    var disabledStrokeColor: Int = 0
    var selectedStrokeColor: Int = 0

    var bgColor: Int = 0
    var pressedBgColor: Int = 0
    var disabledBgColor: Int = 0
    var selectedBgColor: Int = 0

    var shape: Int = RECTANGLE
    var gradientType: Int = -1
    var gradientUseLevel: Boolean = false
    var gradientAngle: Int = -1
    var gradientCenterX: Int = 0
    var gradientCenterY: Int = 0
    var gradientGradientRadius: Int = 0
    var gradientStartColor: Int = 0
    var gradientCenterColor: Int = 0
    var gradientEndColor: Int = 0

    private fun getBgDrawable(): StateListDrawable?{
        return StateListDrawable().apply {
            if(disabledBgColor != 0 || (disabledStrokeColor != 0 && disabledStrokeVisible)){
                val disabledBgColor = if(disabledBgColor != 0) disabledBgColor else bgColor
                val disabledStrokeColor = if(disabledStrokeColor != 0) disabledStrokeColor else strokeColor
                addState(state_disabled,getGradientBgDrawable(disabledBgColor,strokeWidth,disabledStrokeColor))
            }

            if(pressedBgColor != 0 || (pressedStrokeColor != 0 && pressedStrokeVisible)){
                val pressedBgColor = if(pressedBgColor != 0) pressedBgColor else bgColor
                val pressedStrokeColor = if(pressedStrokeColor != 0) pressedStrokeColor else strokeColor
                addState(state_pressed,getGradientBgDrawable(pressedBgColor,strokeWidth,pressedStrokeColor))
            }

            if(selectedBgColor != 0 || (selectedStrokeColor != 0 && selectedStrokeVisible)){
                val selectedBgColor = if(selectedBgColor != 0) selectedBgColor else bgColor
                val selectedStrokeColor = if(selectedStrokeColor != 0) selectedStrokeColor else strokeColor
                addState(state_selected,getGradientBgDrawable(selectedBgColor,strokeWidth,selectedStrokeColor))
            }

            addState(state_none,getGradientBgDrawable(bgColor,strokeWidth,strokeColor))
        }
    }

    private fun getGradientBgDrawable(bgColor: Int,strokeWidth: Int,strokeColor: Int): Drawable {
        return GradientDrawable().apply {
            setShape(this)
            setColor(bgColor)
            setStroke(this,strokeWidth,strokeColor)
            setRadius(this)
            setGradient(this)
        }
    }

    private fun setShape(gradientDrawable: GradientDrawable){
        val shapeType = when(shape){
            RECTANGLE -> GradientDrawable.RECTANGLE
            OVAL -> GradientDrawable.OVAL
            LINE -> GradientDrawable.LINE
            RING -> GradientDrawable.RING
            else -> -1
        }
        if(shapeType >= 0){
            gradientDrawable.shape = shapeType
        }
    }
    
    private fun setStroke(gradientDrawable: GradientDrawable,strokeWidth: Int,strokeColor: Int){
        if((strokeWidth > 0 || strokeDashWidth > 0) && strokeColor != 0){
            gradientDrawable.setStroke(strokeWidth,strokeColor,strokeDashWidth.toFloat(),strokeDashGap.toFloat())
        }
    }
    
    private fun setRadius(gradientDrawable: GradientDrawable){
        if(shape != RECTANGLE){
            return
        }
        gradientDrawable.apply {
            if(this@ShapeBuilder.cornerRadius > 0f){
                cornerRadius = this@ShapeBuilder.cornerRadius
            }else if(topLeftRadius > 0f || topRightRadius > 0f || bottomLeftRadius > 0f || bottomRightRadius > 0f){
                cornerRadii = floatArrayOf(topLeftRadius,topLeftRadius,topRightRadius,topRightRadius,
                        bottomRightRadius,bottomRightRadius,bottomLeftRadius,bottomLeftRadius)
            }
        }
    }

    private fun setGradient(gradientDrawable: GradientDrawable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return
        }
        if(gradientAngle < 0){
            return
        }
        gradientDrawable.apply {
            getGradientOrientationByAngle(gradientAngle)?.let { orientation = it }
            setGradientColor(this)
            setGradientType(this)

            useLevel = gradientUseLevel
            if(this@ShapeBuilder.gradientCenterX != 0 || this@ShapeBuilder.gradientCenterY != 0){
                setGradientCenter(this@ShapeBuilder.gradientCenterX.toFloat(),this@ShapeBuilder.gradientCenterY.toFloat())
            }
        }
    }

    private fun setGradientColor(gradientDrawable: GradientDrawable){
        if(gradientStartColor != 0 || gradientEndColor != 0){
            gradientDrawable.colors = if(gradientCenterColor == 0){
                intArrayOf(gradientStartColor,gradientEndColor)
            }else{
                intArrayOf(gradientStartColor,gradientCenterColor,gradientEndColor)
            }
        }
    }

    private fun setGradientType(gradientDrawable: GradientDrawable){
        val gradientTypeFinal = when(gradientType){
            LINEAR_GRADIENT -> GradientDrawable.LINEAR_GRADIENT
            RADIAL_GRADIENT -> GradientDrawable.RADIAL_GRADIENT
            SWEEP_GRADIENT -> GradientDrawable.SWEEP_GRADIENT
            else -> -1
        }
        if(gradientTypeFinal >= 0){
            gradientDrawable.gradientType = gradientTypeFinal
        }
    }


    /**
     * 设置颜色渐变类型
     *
     * @param gradientAngle gradientAngle
     * @return Orientation
     */
    private fun getGradientOrientationByAngle(gradientAngle: Int): GradientDrawable.Orientation? {
        return when (gradientAngle) {
            0 -> GradientDrawable.Orientation.LEFT_RIGHT
            45 -> GradientDrawable.Orientation.BL_TR
            90 -> GradientDrawable.Orientation.BOTTOM_TOP
            135 -> GradientDrawable.Orientation.BR_TL
            180 -> GradientDrawable.Orientation.RIGHT_LEFT
            225 -> GradientDrawable.Orientation.TR_BL
            270 -> GradientDrawable.Orientation.TOP_BOTTOM
            315 -> GradientDrawable.Orientation.TL_BR
            else -> null
        }
    }
    
    

    fun into(view: View){
        getBgDrawable()?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.background = it
            }else{
                view.setBackgroundDrawable(it)
            }
        }

    }
}