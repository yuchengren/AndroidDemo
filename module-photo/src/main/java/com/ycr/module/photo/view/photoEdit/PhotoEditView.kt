package com.ycr.module.photo.view.photoEdit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.annotation.Nullable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.ycr.module.photo.R

/**
 * 图片编辑View
 * Created by yuchengren on 2018/11/1.
 */
class PhotoEditView : View {
    companion object {
        const val SCALE_MAX = 3f //缩放的最大倍数
    }
    private val modeArray = arrayOf(ImageEditMode.NONE, ImageEditMode.GRAFFITI, ImageEditMode.CLIP)

    private var imageController: ImageController? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PhotoEditView, defStyleAttr, 0)


        typedArray?.run {
            imageController = ImageController(this@PhotoEditView,modeArray[getInt(R.styleable.PhotoEditView_mode,0)],ImageEditParams().apply {
                imageMatchPercent = getFloat(R.styleable.PhotoEditView_imageMatchPercent, 1f)
                shadowColor = getColor(R.styleable.PhotoEditView_shadowColor,0x7F000000)
                clipColor = getColor(R.styleable.PhotoEditView_clipColor, ContextCompat.getColor(context, android.R.color.white))
                clipCornerWidth = getDimensionPixelSize(R.styleable.PhotoEditView_clipCornerWidth, 48)
                clipCornerLineWidth = getDimensionPixelSize(R.styleable.PhotoEditView_clipCornerLineWidth, 4)
                clipBorderLineWidth = getDimensionPixelSize(R.styleable.PhotoEditView_clipBorderLineWidth, 1)
                clipSpanLineWidth = getDimensionPixelSize(R.styleable.PhotoEditView_clipSpanLineWidth, 1)
                clipRowSpans = getInt(R.styleable.PhotoEditView_clipRowSpans,3)
                clipColumnSpans = getInt(R.styleable.PhotoEditView_clipColumnSpans,3)
            })
            recycle()
        }

    }

    fun setImageBitmap(bitmap: Bitmap?) {
        imageController?.setImageBitmap(bitmap)
    }

    fun save(): Bitmap? {
        return imageController?.save()
    }

    override fun onDraw(canvas: Canvas) {
        imageController?.draw(canvas)
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return imageController?.onTouchEvent(event)?:false
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            imageController?.onWindowSizeChanged((right - left).toFloat(), (bottom - top).toFloat())
        }
    }

    fun setMode(mode: ImageEditMode){
        imageController?.setMode(mode)
    }

    fun rotate(rotate: Int){
        imageController?.rotate(rotate)
    }
}