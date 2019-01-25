package com.ycr.module.photo.view.photoEdit

import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.support.annotation.Nullable
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import com.ycr.module.base.view.imageedit.ImgHelper
import com.ycr.module.base.view.imageedit.ImgHomingAnimator
import com.ycr.module.base.view.imageedit.ImgHomingValues
import com.ycr.module.photo.R

/**
 * 图片编辑View
 * Created by yuchengren on 2018/11/1.
 */
class PhotoEditView : View {
    companion object {
        const val SCALE_MAX = 3f //缩放的最大倍数
    }

    private val modeArray = arrayOf(Mode.NONE, Mode.GRAFFITI, Mode.CLIP)

    var imageInitHeightMatchPercent = 1f
    var imageInitWidthMatchPercent = 1f

    var mode: Mode = Mode.NONE

    private var mainHandler: Handler = Handler(Looper.getMainLooper())
    private var bitmap: Bitmap? = null


    private lateinit var gestureDetector: GestureDetector
    private lateinit var scaleGestureDetector: ScaleGestureDetector

    private val M = Matrix()
    private var windowRectF = RectF() //控件可视化区域
    private var imageRectF = RectF() //完整图片区域
    private var hasInitHoming = false //是否已经初始化原始图片状态
    private var imageScale: Float = 1f //图片的当前已缩放的比例
    private var initScale: Float = 1f //图片居中适配屏幕的初始化缩放比例
    private var homingAnimator: ImgHomingAnimator? = null // 图片归位动画

    private fun initPaint() {
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PhotoEditView, defStyleAttr, 0)
        typedArray?.run {
            imageInitHeightMatchPercent = getFloat(R.styleable.PhotoEditView_imageInitHeightMatchPercent, 1f)
            imageInitWidthMatchPercent = getFloat(R.styleable.PhotoEditView_imageInitWidthMatchPercent, 1f)
            val modeIndex = getInt(R.styleable.PhotoEditView_mode,0)
            mode = modeArray[modeIndex]
            recycle()
        }
        initPaint()
        initGesture(context)
    }

    private fun initGesture(context: Context) {
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent?): Boolean {
                return true
            }

            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                return scroll(distanceX, distanceY)
            }

        })

        scaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.OnScaleGestureListener {
            override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
                return true
            }

            override fun onScaleEnd(detector: ScaleGestureDetector?) {}
            /**
             * focusX focusY 返回组成缩放手势(两个手指)中点x横纵坐标
             * scaleFactor 获取本次缩放事件的缩放因子
             * return 返回值表示是否下次缩放需要重置，如果返回ture，那么detector就会重置缩放事件，如果返回false，detector会在之前的缩放上继续进行计算
             */
            override fun onScale(detector: ScaleGestureDetector?): Boolean {
                if (detector == null) {
                    return false
                }
                scale(detector.scaleFactor, scrollX + detector.focusX, scrollY + detector.focusY)
                return true
            }
        })
    }

    private fun getScale(): Float {
        val bitmap: Bitmap = bitmap ?: return 1f
        return imageRectF.width() / bitmap.width
    }

    private fun scale(scaleFactor: Float, px: Float, py: Float) {
        if (scaleFactor == 1f || scaleFactor * imageScale / initScale > SCALE_MAX) {
            return
        }
        M.apply { setScale(scaleFactor, scaleFactor, px, py) }.mapRect(imageRectF)
        imageScale *= scaleFactor
        invalidate()

    }

    private fun scroll(distanceX: Float, distanceY: Float): Boolean {
        if (distanceX != 0f || distanceY != 0f) {
            scrollBy(distanceX.toInt(), distanceY.toInt())
            return true
        }
        return false
    }

    fun setImageBitmap(bitmap: Bitmap?) {
        if (bitmap == null) {
            return
        }
        this.bitmap = bitmap
        hasInitHoming = false
        initHoming()
        invalidate()
    }

    fun save(): Bitmap? {
        val bitmap = bitmap ?: return null
        val createBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(createBitmap)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        return createBitmap
    }

    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) {
            return
        }
        drawBitmap(canvas)
    }

    private fun drawBitmap(canvas: Canvas) {
        val bitmap: Bitmap? = bitmap
        if (bitmap == null || bitmap.isRecycled) {
            return
        }
        canvas.drawBitmap(bitmap, null, imageRectF, null)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return super.onTouchEvent(event)
        }
        if (isHoming()) {
            return false
        }
        val handled: Boolean = if (event.pointerCount > 1) {
            scaleGestureDetector.onTouchEvent(event).or(gestureDetector.onTouchEvent(event))
        } else {
            onTouchEventPath(event)
        }

        when (event.actionMasked) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> homing()
        }

        return handled
    }

    private fun onTouchEventPath(event: MotionEvent?): Boolean {
        if (event == null) {
            return super.onTouchEvent(event)
        }
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
            }
        }
        return false
    }


    private fun isPointInImageRecF(x: Float, y: Float): Boolean {
        val pointArray = floatArrayOf(x, y)
        M.apply {
            setTranslate(scrollX.toFloat(), scrollY.toFloat())
        }.mapPoints(pointArray)
        return imageRectF.contains(pointArray[0], pointArray[1])
    }

    private fun getOriginPathMatrix(): Matrix {
        return M.apply {
            setTranslate(scrollX.toFloat(), scrollY.toFloat())
            postTranslate(-imageRectF.left, -imageRectF.top)
            val scale: Float = 1 / imageScale
            postScale(scale, scale)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            onWindowSizeChanged((right - left).toFloat(), (bottom - top).toFloat())
        }
    }

    /**
     * 当View窗口大小发生变化时
     */
    private fun onWindowSizeChanged(width: Float, height: Float) {
        if (width == 0f || height == 0f) {
            return
        }
        windowRectF.set(0f, 0f, width, height)
        initHoming()
    }

    private fun initHoming() {
        val bitmap = bitmap ?: return
        if (windowRectF.isEmpty) {
            return
        }
        imageRectF.set(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat())

        val scale = Math.min(windowRectF.width() * imageInitWidthMatchPercent / imageRectF.width(),
                windowRectF.height() * imageInitHeightMatchPercent / imageRectF.height())
        //图片适配View窗口大小
        M.setScale(scale, scale, imageRectF.centerX(), imageRectF.centerY())
        //图片平移至View窗口居中
        M.postTranslate(windowRectF.centerX() - imageRectF.centerX(), windowRectF.centerY() - imageRectF.centerY())
        M.mapRect(imageRectF) //通过矩阵变换矩形
        imageScale = scale

        initScale = scale
        hasInitHoming = true
    }

    private fun isHoming(): Boolean {
        return homingAnimator?.isRunning ?: false
    }

    private fun stopHoming() {
        homingAnimator?.cancel()
    }

    /**
     * 图片归位
     */
    private fun homing() {
        stopHoming()
        startHoming()
    }

    private fun startHoming() {
        if (homingAnimator == null) {
            homingAnimator = ImgHomingAnimator().apply {
                addUpdateListener {
                    val imageHomingValues = it.animatedValue as ImgHomingValues
                    scroll(imageHomingValues.x - scrollX, imageHomingValues.y - scrollY)
                    scale(imageHomingValues.scale / imageScale, imageRectF.centerX(), imageRectF.centerY())
                }
                addListener(object : AnimatorListenerAdapter() {})
            }
        }
        homingAnimator?.setHomingValues(getStartHomingValues(), getEndHomingValues())
        homingAnimator?.start()
    }

    private fun getStartHomingValues(): ImgHomingValues {
        return ImgHomingValues(scrollX.toFloat(), scrollY.toFloat(), imageScale)
    }

    private fun getEndHomingValues(): ImgHomingValues {
        val homingValues = getStartHomingValues()
        val imgRectF = RectF()
        M.reset()
        M.mapRect(imgRectF, imageRectF)

        val winRectF = RectF(windowRectF)
        winRectF.offset(scrollX.toFloat(), scrollY.toFloat())

        homingValues.scrollConcat(ImgHelper.getFitHomingValues(winRectF, imgRectF))
        return homingValues
    }

    enum class Mode(val mode: Int){
        NONE(0),
        GRAFFITI(1),
        CLIP(2)
    }
}