package com.ycr.module.photo.view.photoEdit

import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.*
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import com.ycr.module.base.view.imageedit.ImgHelper
import com.ycr.module.base.view.imageedit.ImgHomingAnimator
import com.ycr.module.base.view.imageedit.ImgHomingValues

/**
 * Created by yuchengren on 2019/1/27.
 */
class ImageController(private val view: View, private var mode: ImageEditMode, var params: ImageEditParams) {
    private lateinit var gestureDetector: GestureDetector
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private lateinit var imageClipController: ImageClipController
    private var bitmap: Bitmap? = null
    private val M = Matrix()
    private var windowRectF = RectF() //控件可视化区域
    private var imageRectF = RectF() //完整图片区域
    private var hasInitHoming = false //是否已经初始化原始图片状态
    private var imageScale: Float = 1f //图片的当前已缩放的比例
    private var imageRotate: Float = 0f //图片当前已旋转的角度0~360
    private var initScale: Float = 1f //图片居中适配屏幕的初始化缩放比例
    private lateinit var shadowPaint: Paint
    private lateinit var shadowPath: Path
    private var isTouching = false

    init {
        if(isClipMode()){
            initClipController()
            initShadowPaintAndPath()
        }

        initGesture(view.context)
    }

    private fun initShadowPaintAndPath() {
        shadowPath = Path()
        shadowPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = params.shadowColor
        }
    }

    private fun initGesture(context: Context) {
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent?): Boolean { return true }
            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                return scroll(distanceX, distanceY)
            }
        })

        scaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.OnScaleGestureListener {
            override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean { return true }
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
                scale(detector.scaleFactor, view.scrollX + detector.focusX, view.scrollY + detector.focusY)
                return true
            }
        })
    }

    private fun scroll(distanceX: Float, distanceY: Float): Boolean {
        if (distanceX != 0f || distanceY != 0f) {
            if(isClipMode()){
                if(imageClipController.scroll(distanceX,distanceY)){
                    view.invalidate()
                    return true
                }
            }
            view.scrollBy(distanceX.toInt(), distanceY.toInt())
            return true
        }
        return false
    }

    private fun initClipController(){
        imageClipController = ImageClipController(params.clipColor,params.clipCornerWidth,
                params.clipCornerLineWidth,params.clipBorderLineWidth, params.clipSpanLineWidth,
                params.clipRowSpans,params.clipColumnSpans)
    }

    fun setMode(mode: ImageEditMode){
        if(this.mode != mode){
            onModeChanged()
        }
    }

    private fun isClipMode(): Boolean{
        return mode == ImageEditMode.CLIP
    }

    private fun onModeChanged() {
        this.mode = mode
        if(isClipMode()){
            initClipController()
            initShadowPaintAndPath()
            hasInitHoming = false
            initHoming()
            view.invalidate()
        }
    }

    fun setImageBitmap(bitmap: Bitmap?) {
        if (bitmap == null) {
            return
        }
        this.bitmap = bitmap
        hasInitHoming = false
        initHoming()
        view.invalidate()
    }

    fun onWindowSizeChanged(width: Float, height: Float) {
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

        val scale = Math.min(windowRectF.width() * params.imageMatchPercent / imageRectF.width(),
                windowRectF.height() * params.imageMatchPercent / imageRectF.height())
        //图片适配View窗口大小
        M.setScale(scale, scale, imageRectF.centerX(), imageRectF.centerY())
        //图片平移至View窗口居中
        M.postTranslate(windowRectF.centerX() - imageRectF.centerX(), windowRectF.centerY() - imageRectF.centerY())
        M.mapRect(imageRectF) //通过矩阵变换矩形

        if(isClipMode()){
            imageClipController.clipInitRectF.set(imageRectF) //裁剪初始化区域与图片初始化区域相同
            imageClipController.clipRectF.set(imageRectF)
        }
        imageScale = scale

        initScale = scale
        hasInitHoming = true
    }

    private fun getScale(): Float {
        val bitmap: Bitmap = bitmap ?: return 1f
        return imageRectF.width() / bitmap.width
    }

    fun scale(scaleFactor: Float, px: Float, py: Float) {
        if (scaleFactor == 1f || scaleFactor * imageScale / initScale > PhotoEditView.SCALE_MAX) {
            return
        }
        M.apply { setScale(scaleFactor, scaleFactor, px, py) }.mapRect(imageRectF)
        imageScale *= scaleFactor
        view.invalidate()
    }

    fun rotate(rotate: Int) {
        if(isHoming()){
            return
        }
        imageRotate = Math.round((imageRotate + rotate) / 90f) * 90f % 360
        M.setRotate(imageRotate,imageRectF.centerX(),imageRectF.centerY())

    }

    fun draw(canvas: Canvas, isSave: Boolean = false) {
        drawBitmap(canvas)
        if(!isSave){
            drawClipShadow(canvas)
            drawClip(canvas)
        }
    }

    private fun drawClip(canvas: Canvas) {
        if(isClipMode()){
            canvas.save()
            canvas.translate(view.scrollX.toFloat(),view.scrollY.toFloat())
            imageClipController.drawClip(canvas)
            canvas.restore()
        }
    }

    private fun drawClipShadow(canvas: Canvas) {
        if(isClipMode() && !isTouching){
            shadowPath.reset()
            shadowPath.addRect(imageRectF,Path.Direction.CW)
            shadowPath.addRect(imageClipController.clipRectF,Path.Direction.CCW)
            canvas.drawPath(shadowPath,shadowPaint)
        }
    }

    private fun drawBitmap(canvas: Canvas) {
        val bitmap: Bitmap? = bitmap
        if (bitmap == null || bitmap.isRecycled) {
            return
        }
        canvas.drawBitmap(bitmap, null, imageRectF, null)
    }

    fun save(): Bitmap? {
        val saveRectF = if(isClipMode()) imageClipController.clipRectF else imageRectF

        val bitmap = bitmap ?: return null
        val createBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(createBitmap)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        return createBitmap
    }

    fun onTouchEvent(event: MotionEvent): Boolean {
        if (isHoming()) {
            return false
        }
        var handled: Boolean = scaleGestureDetector.onTouchEvent(event) or when(mode){
            ImageEditMode.NONE,ImageEditMode.CLIP ->
                onTouchNone(event)
            else->{
                if(event.pointerCount > 1){
                    onPathDone(event)
                    onTouchNone(event)
                }else{
                    onTouchPath(event)
                }
            }
        }

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                onTouchDown(event)
            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> onTouchUp(event)
        }
        return handled
    }

    private fun onTouchNone(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private fun onTouchDown(event: MotionEvent) {
        isTouching = true
        if(isClipMode()){
            imageClipController.onTouchDown(event)
        }
    }

    private fun onTouchUp(event: MotionEvent) {
        if(isClipMode()){
            imageClipController.onTouchUp(event)
        }
        isTouching = false
        view.invalidate()
        homing()
    }



    private var homingAnimator: ImgHomingAnimator? = null // 图片归位动画

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
                    view.scrollTo(imageHomingValues.x.toInt(), imageHomingValues.y.toInt())
                    scale(imageHomingValues.scale / imageScale, imageRectF.centerX(), imageRectF.centerY())
                }
                addListener(object : AnimatorListenerAdapter() {})
            }
        }
        homingAnimator?.setHomingValues(getStartHomingValues(), getEndHomingValues())
        homingAnimator?.start()
    }

    private fun getStartHomingValues(): ImgHomingValues {
        return ImgHomingValues(view.scrollX.toFloat(), view.scrollY.toFloat(), imageScale)
    }

    private fun getEndHomingValues(): ImgHomingValues {
        val homingValues = getStartHomingValues()
        val imgRectF = RectF()
        M.reset()
        M.mapRect(imgRectF, imageRectF)

        val winRectF = RectF()
        M.reset()
        M.setScale(params.imageMatchPercent,params.imageMatchPercent,windowRectF.centerX(),windowRectF.centerY())
        M.mapRect(winRectF,windowRectF)
        winRectF.offset(view.scrollX.toFloat(), view.scrollY.toFloat())
        homingValues.scrollConcat(ImgHelper.getFitHomingValues(winRectF, imgRectF))
        return homingValues
    }

    private fun onTouchPath(event: MotionEvent): Boolean { return false }
    private fun onPathBegin(event: MotionEvent){}
    private fun onPathMove(event: MotionEvent){}
    private fun onPathDone(event: MotionEvent) {}


}