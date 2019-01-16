package com.ycr.lib.ui.view.gridimage

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.Nullable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.*
import com.ycr.lib.ui.R

/**
 * Created by yuchengren on 2018/12/27.
 */
class GridImageView: AppCompatImageView {

    private val strokePaint = Paint()
    private val imagePaint: Paint =  Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
    private var windowRectF = RectF() //控件可视化区域
    private var imageRectF = RectF() //图片区域
    private var strokeRectF = RectF() //描边区域
    private var checkDrawableRectF = RectF() // 选中状态图标的区域
    private val imageMatrixArray = FloatArray(9)

    var strokeWidth = 0 //描边宽度
        set(value) {
            field = value
            strokePaint.strokeWidth = value.toFloat()
        }

    var strokeVisible: Boolean = false //描边是否可见
    var strokeColor: Int = 0 //描边颜色
        set(value) {
            field = value
            strokePaint.color = value
        }
    var w: Int = 0
    var h: Int = 0
    var ratio: Float = 1f //宽高比
    var cornerRadius = 0 //圆角度数

    var checkEnabled = false //选中功能是否可用
    var checked = false //选中图标状态
    var checkedDrawableResId = -1
    var notCheckedDrawableResId = -1
    var checkDrawableWidth = 0f
    var checkDrawableHeight = 0f
    var checkDrawableHorizontalMargin = 0f
    var checkDrawableVerticalMargin = 0f
    var checkDrawableGravity = Gravity.RIGHT or Gravity.TOP

    private lateinit var gestureDetector: GestureDetector

    constructor(context: Context) : this(context, null)

    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.GridImageView, defStyleAttr, 0)
        typeArray.run {
            strokeWidth = getDimensionPixelSize(R.styleable.GridImageView_strokeWidth,0)
            strokeVisible = getBoolean(R.styleable.GridImageView_strokeVisible,false)
            strokeColor = getColor(R.styleable.GridImageView_strokeColor,ContextCompat.getColor(context,android.R.color.white))

            cornerRadius = getDimensionPixelSize(R.styleable.GridImageView_cornerRadius,0)
            ratio = typeArray.getFloat(R.styleable.GridImageView_ratio, 1.0f)

            checked = getBoolean(R.styleable.GridImageView_checked, false)
            checkEnabled = getBoolean(R.styleable.GridImageView_checkEnabled,false)
            checkedDrawableResId = getResourceId(R.styleable.GridImageView_checkedDrawableResId, -1)
            notCheckedDrawableResId = getResourceId(R.styleable.GridImageView_notCheckedDrawableResId, -1)
            checkDrawableWidth = getDimensionPixelSize(R.styleable.GridImageView_checkDrawableWidth,0).toFloat()
            checkDrawableHeight = getDimensionPixelSize(R.styleable.GridImageView_checkDrawableHeight,0).toFloat()
            checkDrawableHorizontalMargin = getDimensionPixelSize(R.styleable.GridImageView_checkDrawableHorizontalMargin,0).toFloat()
            checkDrawableVerticalMargin = getDimensionPixelSize(R.styleable.GridImageView_checkDrawableVerticalMargin,0).toFloat()
            checkDrawableGravity = getInt(R.styleable.GridImageView_checkDrawableGravity,Gravity.RIGHT or Gravity.TOP)
        }
        typeArray.recycle()
        initStrokePaint()
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent?): Boolean {
                return true
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                if(checkDrawableRectF.contains(e.x,e.y)){
                    checked = !checked
                    invalidate()
                }
                return true
            }

        })
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    private fun initStrokePaint() {
        strokePaint.style = Paint.Style.STROKE
        strokePaint.isAntiAlias = true
        strokePaint.strokeWidth = strokeWidth.toFloat()
        strokePaint.color = strokeColor
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        setImageDrawable(ContextCompat.getDrawable(context,resId))
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        imagePaint.shader = BitmapShader((drawable as? BitmapDrawable)?.bitmap?:return, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthSpec = if(w == 0) widthMeasureSpec else View.MeasureSpec.makeMeasureSpec(w, View.MeasureSpec.EXACTLY)
        var heightSpec = if(h == 0) widthMeasureSpec else View.MeasureSpec.makeMeasureSpec(h, View.MeasureSpec.EXACTLY)
        //获取宽度的模式和尺寸
        var widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        //获取高度的模式和尺寸
        var heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        //宽确定，高不确定
        if (widthMode == View.MeasureSpec.EXACTLY && (heightMode != View.MeasureSpec.EXACTLY || heightSize == 0) && ratio != 0f) {
            heightSize = (widthSize / ratio + 0.5f).toInt()//根据宽度和比例计算高度
            heightSpec = View.MeasureSpec.makeMeasureSpec(heightSize, View.MeasureSpec.EXACTLY)
        } else if ((widthMode != View.MeasureSpec.EXACTLY || widthSize == 0) && heightMode == View.MeasureSpec.EXACTLY && ratio != 0f) {
            widthSize = (heightSize * ratio + 0.5f).toInt()
            widthSpec = View.MeasureSpec.makeMeasureSpec(widthSize, View.MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthSpec,heightSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if(changed){
            onWindowSizeChanged((right - left).toFloat(), (bottom - top).toFloat())
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    /**
     * 当View窗口大小发生变化时
     */
    private fun onWindowSizeChanged(width: Float, height: Float) {
        if (width == 0f || height == 0f) {
            return
        }
        windowRectF.set(0f, 0f, width, height)

        val offset = strokeWidth / 2f
        imageRectF.set(paddingLeft + offset, paddingTop + offset,
        width - paddingRight -offset, height - paddingBottom - offset)
        strokeRectF.set(offset, offset, width -offset, height - offset)

        updateCheckDrawableRectF(width, height)
    }

    private fun updateCheckDrawableRectF(winWidth: Float, winHeight: Float) {
        if(!checkEnabled){
            return
        }
        val andX = 1.shl(Gravity.AXIS_Y_SHIFT) - 1
        val andY = 1.shl(Gravity.AXIS_Y_SHIFT * 2) - 1 - andX
        val gravityX = checkDrawableGravity and andX
        val gravityY = checkDrawableGravity and andY

        val left: Float
        val right: Float
        val top: Float
        val bottom: Float
        when(gravityX){
            Gravity.LEFT ->{
                left = checkDrawableHorizontalMargin
                right = left + checkDrawableWidth
            }
            Gravity.RIGHT ->{
                right = winWidth - checkDrawableHorizontalMargin
                left = right - checkDrawableWidth
            }
            else ->{
                left = (winWidth - checkDrawableWidth) / 2
                right = left + checkDrawableWidth
            }
        }
        when(gravityY){
            Gravity.TOP ->{
                top = checkDrawableHorizontalMargin
                bottom = top + checkDrawableHeight
            }
            Gravity.BOTTOM ->{
                bottom = winHeight - checkDrawableVerticalMargin
                top = bottom - checkDrawableHeight
            }
            else ->{
                top = (winHeight - checkDrawableHeight) / 2
                bottom = top + checkDrawableHeight
            }
        }
        checkDrawableRectF.set(left,top,right,bottom)
    }

    override fun onDraw(canvas: Canvas) {
        if (drawable == null) {
            return
        }
        if (drawable.intrinsicWidth == 0 || drawable.intrinsicHeight == 0) {
            return
        }
        drawRoundImage(canvas)
        drawStroke(canvas)
        drawCheckBitmap(canvas)
    }

    private fun drawRoundImage(canvas: Canvas) {
        imageMatrix.getValues(imageMatrixArray)
        val scaleX = imageMatrixArray[0]
        val scaleY = imageMatrixArray[4]

        if(cornerRadius == 0 || (drawable.intrinsicWidth * scaleX < width - paddingLeft - paddingRight &&
                        drawable.intrinsicWidth * scaleY < height - paddingTop - paddingBottom)){
            super.onDraw(canvas)
        }else{
            if (imageMatrix == null && paddingTop == 0 && paddingLeft == 0) {
                drawable.draw(canvas)
            } else {
                val saveCount = canvas.saveCount
                canvas.save()
                if (cropToPadding) {
                    canvas.clipRect(scrollX + paddingLeft,
                            scrollY + paddingTop,
                            scrollX + right - left - paddingRight,
                            scrollY + bottom - top - paddingBottom)
                }
                canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())
                imagePaint.shader?.setLocalMatrix(imageMatrix)
                canvas.drawRoundRect(imageRectF,cornerRadius.toFloat(), cornerRadius.toFloat(), imagePaint)
                canvas.restoreToCount(saveCount)
            }
        }
    }

    private fun drawStroke(canvas: Canvas) {
        val isNeedDrawStroke = strokeVisible && strokeWidth > 0
        if(isNeedDrawStroke){
            canvas.drawRoundRect(strokeRectF, cornerRadius.toFloat(),cornerRadius.toFloat(),strokePaint)
        }
    }

    private fun drawCheckBitmap(canvas: Canvas) {
        if(checkEnabled){
            val checkDrawableResId = if(checked) checkedDrawableResId else notCheckedDrawableResId
            if(checkDrawableResId > 0){
                BitmapFactory.decodeResource(resources, checkDrawableResId)?.let {
                    canvas.drawBitmap(it,null,checkDrawableRectF,null)
                }
            }
        }
    }
}