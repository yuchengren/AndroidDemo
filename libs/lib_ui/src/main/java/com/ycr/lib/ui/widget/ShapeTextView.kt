package com.ycr.lib.ui.widget

import android.content.Context
import android.support.annotation.Nullable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.Gravity
import com.ycr.lib.ui.R
import com.ycr.lib.ui.util.ColorStateBuilder
import com.ycr.lib.ui.util.ShapeBuilder

/**
 * created by yuchengren on 2019-06-12
 */
class ShapeTextView @JvmOverloads constructor(context: Context, @Nullable attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        AppCompatTextView(context, attrs, defStyleAttr) {

    private val shapeBuilder: ShapeBuilder = ShapeBuilder()
    private val colorStateBuilder: ColorStateBuilder = ColorStateBuilder()

    init {

        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeTextView,defStyleAttr, 0)
        typeArray.run {
            val gravity = getInt(R.styleable.ShapeTextView_android_gravity,Gravity.CENTER)
            if(getGravity() != gravity){
                setGravity(gravity)
            }

            val clickable = getBoolean(R.styleable.ShapeTextView_android_clickable,true)
            if(isClickable != clickable){
                isClickable = clickable
            }

            shapeBuilder.cornerRadius = getDimensionPixelOffset(R.styleable.ShapeTextView_cornerRadius,0).toFloat()
            shapeBuilder.topLeftRadius = getDimensionPixelOffset(R.styleable.ShapeTextView_topLeftRadius,0).toFloat()
            shapeBuilder.topRightRadius = getDimensionPixelOffset(R.styleable.ShapeTextView_topRightRadius,0).toFloat()
            shapeBuilder.bottomRightRadius = getDimensionPixelOffset(R.styleable.ShapeTextView_bottomRightRadius,0).toFloat()
            shapeBuilder.bottomLeftRadius = getDimensionPixelOffset(R.styleable.ShapeTextView_bottomLeftRadius,0).toFloat()

            shapeBuilder.strokeWidth = getDimensionPixelOffset(R.styleable.ShapeTextView_strokeWidth,0)
            shapeBuilder.strokeColor = getColor(R.styleable.ShapeTextView_strokeColor,0)
            shapeBuilder.strokeDashWidth = getDimensionPixelOffset(R.styleable.ShapeTextView_strokeDashWidth,0)
            shapeBuilder.strokeDashGap = getDimensionPixelOffset(R.styleable.ShapeTextView_strokeDashGap,0)


            shapeBuilder.pressedStrokeVisible = getBoolean(R.styleable.ShapeTextView_pressedStrokeVisible,true)
            shapeBuilder.disabledStrokeVisible = getBoolean(R.styleable.ShapeTextView_disabledStrokeVisible,true)
            shapeBuilder.selectedStrokeVisible = getBoolean(R.styleable.ShapeTextView_selectedStrokeVisible,true)

            shapeBuilder.pressedStrokeColor = getColor(R.styleable.ShapeTextView_pressedStrokeColor,0)
            shapeBuilder.disabledStrokeColor = getColor(R.styleable.ShapeTextView_disabledStrokeColor,0)
            shapeBuilder.selectedStrokeColor = getColor(R.styleable.ShapeTextView_selectedStrokeColor,0)

            shapeBuilder.bgColor = getColor(R.styleable.ShapeTextView_bgColor,ContextCompat.getColor(context,android.R.color.white))
            shapeBuilder.pressedBgColor = getColor(R.styleable.ShapeTextView_pressedBgColor,0)
            shapeBuilder.disabledBgColor = getColor(R.styleable.ShapeTextView_disabledBgColor,0)
            shapeBuilder.selectedBgColor = getColor(R.styleable.ShapeTextView_selectedBgColor,0)

            shapeBuilder.shape = getInt(R.styleable.ShapeTextView_shape,ShapeBuilder.RECTANGLE)
            shapeBuilder.gradientType = getInt(R.styleable.ShapeTextView_gradientType,-1)
            shapeBuilder.gradientUseLevel = getBoolean(R.styleable.ShapeTextView_gradientUseLevel,false)
            shapeBuilder.gradientAngle = getInt(R.styleable.ShapeTextView_gradientAngle,-1)
            shapeBuilder.gradientCenterX = getDimensionPixelOffset(R.styleable.ShapeTextView_gradientCenterX,0)
            shapeBuilder.gradientCenterY = getDimensionPixelOffset(R.styleable.ShapeTextView_gradientCenterY,0)
            shapeBuilder.gradientGradientRadius = getInt(R.styleable.ShapeTextView_gradientGradientRadius,0)
            shapeBuilder.gradientStartColor = getInt(R.styleable.ShapeTextView_gradientStartColor,0)
            shapeBuilder.gradientCenterColor = getInt(R.styleable.ShapeTextView_gradientCenterColor,0)
            shapeBuilder.gradientEndColor = getInt(R.styleable.ShapeTextView_gradientEndColor,0)

            colorStateBuilder.defaultTextColor = currentTextColor
            colorStateBuilder.pressedTextColor = getColor(R.styleable.ShapeTextView_pressedTextColor,0)
            colorStateBuilder.disabledTextColor = getColor(R.styleable.ShapeTextView_disabledTextColor,0)
            colorStateBuilder.selectedTextColor = getColor(R.styleable.ShapeTextView_selectedTextColor,0)
        }
        typeArray.recycle()

        shapeBuilder.into(this)
        colorStateBuilder.into(this)
    }
}