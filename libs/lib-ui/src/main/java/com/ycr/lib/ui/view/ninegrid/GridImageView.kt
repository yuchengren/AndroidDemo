package com.ycr.lib.ui.view.ninegrid

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.Nullable
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import com.ycr.kernel.log.LogHelper
import com.ycr.lib.ui.R

/**
 * Created by yuchengren on 2018/12/27.
 */
class GridImageView: AppCompatImageView {

    private val strokePaint = Paint()

    private var strokeWidth: Float = 0f //描边宽度
    private var strokeVisible: Boolean = false //描边是否可见
    private var strokeColor: Int = 0//描边颜色

    private var ratio: Float = 1f //宽高比
    private var cornerRadius: Float = 0f //圆角度数

    constructor(context: Context) : this(context, null)

    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.GridImageView, defStyleAttr, 0)
        typeArray.run {
            strokeWidth = getDimensionPixelSize(R.styleable.GridImageView_strokeWidth,0).toFloat()
            strokeVisible = getBoolean(R.styleable.GridImageView_strokeVisible,false)
            strokeColor = getColor(R.styleable.GridImageView_strokeColor,ContextCompat.getColor(context,android.R.color.white))

            cornerRadius = getDimensionPixelSize(R.styleable.GridImageView_cornerRadius,0).toFloat()
            ratio = typeArray.getFloat(R.styleable.GridImageView_ratio, 0.0f)
            recycle()
        }
        initStrokePaint()
    }

    private fun initStrokePaint() {
        strokePaint.style = Paint.Style.STROKE
        strokePaint.isAntiAlias = true
        strokePaint.strokeWidth = strokeWidth
        strokePaint.color = strokeColor
    }

    fun setStrokeWidth(strokeWidthPixels: Float){
        strokeWidth = strokeWidthPixels
        strokePaint.strokeWidth = strokeWidth
        invalidate()
    }

    fun setStrokeWidth(@DimenRes resId: Int){
        setStrokeWidth(context.resources.getDimensionPixelSize(resId).toFloat())
    }

    fun setStrokeVisible(strokeVisible: Boolean){
        this.strokeVisible = strokeVisible
        invalidate()
    }


    override fun setImageResource(@DrawableRes resId: Int) {
        setImageDrawable(ContextCompat.getDrawable(context,resId))
    }

    override fun setImageDrawable(drawable: Drawable?) {
        if (drawable is BitmapDrawable && cornerRadius > 0){
            super.setImageDrawable(drawable)
//            super.setImageDrawable(getRoundedDrawable(context, (this.drawable as? BitmapDrawable)?.bitmap?:return,cornerRadius))
//            super.setImageDrawable(getRoundedDrawable(context, drawable.bitmap,cornerRadius))
        } else {
            super.setImageDrawable(drawable)
        }



        if(strokeVisible && strokeWidth > 0 && getDrawable() != null){
            val bounds = getDrawable().bounds
            val offset = (strokeWidth / 2f).toInt()
//            if(bounds.width() >= width - strokeWidth.toInt()){
//                bounds.left +=  offset
//                bounds.right -= offset
//            }
//            if(bounds.height() >= height - strokeWidth.toInt()){
//                bounds.top +=  offset
//                bounds.bottom -= offset
//            }
//            getDrawable().setBounds(bounds.left + offset,bounds.top + offset,bounds.right - offset,bounds.bottom - offset)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthSpec = widthMeasureSpec
        var heightSpec = heightMeasureSpec
        //获取宽度的模式和尺寸
        var widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        var widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        //获取高度的模式和尺寸
        var heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        var heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

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

    override fun onDraw(canvas: Canvas) {
//        if(strokeVisible && strokeWidth > 0){
//            val bounds = drawable?.bounds?:return
//            val offset = (strokeWidth / 2f).toInt()
//            if(bounds.width() >= width - strokeWidth.toInt()){
//                bounds.left +=  offset
//                bounds.right -= offset
//            }
//            if(bounds.height() >= height - strokeWidth.toInt()){
//                bounds.top +=  offset
//                bounds.bottom -= offset
//            }
//        }
        if (drawable == null) {
            return  // couldn't resolve the URI
        }

        if ( drawable.intrinsicWidth == 0 || drawable.intrinsicHeight == 0) {
            return      // nothing to draw (empty bounds)
        }

        val mDrawable = drawable
        val mDrawMatrix = imageMatrix

        if (mDrawMatrix == null && paddingTop === 0 && paddingLeft === 0) {
            mDrawable.draw(canvas)
        } else {
            val saveCount = canvas.saveCount
            canvas.save()

            if (cropToPadding) {
                val scrollX = scrollX
                val scrollY = scrollY
                canvas.clipRect(scrollX + paddingLeft, scrollY + paddingTop,
                        scrollX + right - left - paddingRight,
                        scrollY + bottom - top - paddingBottom)
            }

            canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())

            if (mDrawMatrix != null) {
                canvas.concat(mDrawMatrix)
            }

            val bounds = drawable.bounds
//            mDrawable.draw(canvas)
            var mPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
            mPaint.setShader(BitmapShader((drawable as? BitmapDrawable)?.bitmap?:return, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))
//            canvas.drawRoundRect(RectF(bounds.left.toFloat(),bounds.top.toFloat(),bounds.right.toFloat(),bounds.bottom.toFloat()),
//                    cornerRadius, cornerRadius, mPaint)
            val rect = RectF(paddingLeft.toFloat(), paddingTop.toFloat(), (width - paddingRight).toFloat(), (height - paddingBottom).toFloat())
            val matrix = Matrix()
            val array = FloatArray(9)
            mDrawMatrix.getValues(array)
            array.forEach {
                LogHelper.d(it.toString())
            }
            matrix.setScale(1 /array[0],1/ array[4])

            matrix.mapRect(rect)
//            canvas.drawRoundRect(rect,cornerRadius, cornerRadius, mPaint)



//            canvas.drawRoundRect(RectF(paddingLeft.toFloat(), paddingTop.toFloat(), (width - paddingRight).toFloat(), (height - paddingBottom).toFloat()),
//                    cornerRadius, cornerRadius, mPaint)

            canvas.restoreToCount(saveCount)

            val bitmap = (drawable as? BitmapDrawable)?.bitmap ?: return
            val createBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
            val canva = Canvas(createBitmap)
            canva.concat(mDrawMatrix)
            canva.drawBitmap(bitmap, 0f, 0f, null)

            var paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
            paint.setShader(BitmapShader(createBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))

                        canvas.drawRoundRect(RectF(paddingLeft.toFloat(), paddingTop.toFloat(), (width - paddingRight).toFloat(), (height - paddingBottom).toFloat()),
                    cornerRadius, cornerRadius, paint)





        }

//        getRoundedDrawable(context, (this.drawable as? BitmapDrawable)?.bitmap?:return,cornerRadius)
//        super.onDraw(canvas)

        var offset = strokeWidth / 2f
        val rect = canvas.clipBounds
        val strokeRectF = RectF(rect.left + offset,rect.top + offset,rect.right - offset,rect.bottom - offset)
        canvas.drawRoundRect(strokeRectF, cornerRadius,cornerRadius,strokePaint)
    }


    private fun getRoundedDrawable(context: Context, bitmap: Bitmap, cornerRadius: Float): RoundedBitmapDrawable {
        return RoundedBitmapDrawableFactory.create(context.resources, bitmap).apply {
            setAntiAlias(true)
            this.cornerRadius = cornerRadius
        }
    }
}