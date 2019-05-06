package com.ycr.lib.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.annotation.Nullable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.ycr.lib.ui.R

/**
 * 圆环View
 * Created by yuchengren on 2019/1/21.
 */
class CircularView: View {
    var circleColor = 0 //内部圆的颜色
    var circularColor = 0 //外部圆环的颜色

    var circularWidth = 0f //圆环宽度

    private var circlePaint = Paint()
    private var circularPaint = Paint()


    private val circleRectF = RectF()
    private val circularRectF = RectF()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularView, defStyleAttr, 0).apply {
            circleColor = getColor(R.styleable.CircularView_circleColor,ContextCompat.getColor(context,android.R.color.white))
            circularColor = getColor(R.styleable.CircularView_circularColor,ContextCompat.getColor(context,android.R.color.black))
            circularWidth = getDimensionPixelSize(R.styleable.CircularView_circularWidth,0).toFloat()
        }
        typedArray.recycle()
        initCirclePaint()
        initCircularPaint()
    }

    private fun initCirclePaint() {
        circlePaint.run {
            color = circleColor
            style = Paint.Style.FILL
            isAntiAlias = true
        }
    }

    private fun initCircularPaint() {
        circularPaint.run {
            color = circularColor
            style = Paint.Style.STROKE
            strokeWidth = circularWidth
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        circleRectF.set(circularWidth,circularWidth,w - circularWidth,h - circularWidth)
        val halfCircularWidth = circularWidth / 2
        circularRectF.set(halfCircularWidth,halfCircularWidth,w - halfCircularWidth,h - halfCircularWidth)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCirCle(canvas)
        drawCircular(canvas)
    }

    private fun drawCirCle(canvas: Canvas) {
        canvas.drawOval(circleRectF,circlePaint)
    }

    private fun drawCircular(canvas: Canvas) {
        if(circularWidth <= 0){
            return
        }
        canvas.drawArc(circularRectF,0f,360f,false,circularPaint)
    }
}