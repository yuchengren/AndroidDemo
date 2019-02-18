package com.ycr.module.photo.view.photoEdit

import android.animation.Animator
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
    private var imageWinRectF = RectF() //图片的适配可视化窗体区域
    private var imageRectF = RectF() //完整图片区域
    private var clipCanvasRectF = RectF() //裁剪的阴影区域
    private var hasInitHoming = false //是否已经初始化原始图片状态
    private var imageScale: Float = 1f //图片的当前已缩放的比例
    private var imageRotate: Float = 0f //图片当前已旋转的角度0~360
    private var imageTargetRotate: Float = 0f //图片将要旋转到的角度
    private var initScale: Float = 1f //图片居中适配屏幕的初始化缩放比例
    private lateinit var shadowPaint: Paint
    private lateinit var shadowPath: Path
    private var isSteady = true //是否是稳定的

    private var homingAnimator: ImgHomingAnimator? = null // 图片归位动画
    private var isAnimatorCancelled = false

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
                view.invalidate()
                return true
            }
        })
    }

    private fun scroll(distanceX: Float, distanceY: Float): Boolean {
        if (distanceX == 0f || distanceY == 0f) {
            return false
        }
        if(isClipMode()){
            if(imageClipController.scroll(distanceX,distanceY)){

                val imageRotateRectF = RectF()
                M.setRotate(imageRotate,clipCanvasRectF.centerX(),clipCanvasRectF.centerY())
                M.mapRect(imageRotateRectF,imageRectF)

                val scrollClipRectF = imageClipController.getScrollClipRectF(view.scrollX,view.scrollY)
                val homingValues = ImgHomingValues(view.scrollX.toFloat(),view.scrollY.toFloat(),
                        imageScale,imageTargetRotate)
                homingValues.concat(ImgHelper.getFillHomingValues(scrollClipRectF,imageRotateRectF,
                        clipCanvasRectF.centerX(),clipCanvasRectF.centerY()))

                applyHoming(homingValues)
                return true
            }
        }
        doScrollTo(view.scrollX + distanceX.toInt(),view.scrollY + distanceY.toInt())
        return true
    }

    private fun applyHoming(imgHomingValues: ImgHomingValues){
        imageRotate = imgHomingValues.rotate
        scale(imgHomingValues.scale / imageScale, clipCanvasRectF.centerX(), clipCanvasRectF.centerY())
        if(!doScrollTo(imgHomingValues.x.toInt(),imgHomingValues.y.toInt())){
//            resetClipRectF()
            view.invalidate()
        }
    }

    private fun doScrollTo(x: Int,y: Int): Boolean{
        if(x != view.scrollX || y != view.scrollY){
//            M.setTranslate((x - view.scrollX).toFloat(),(y - view.scrollY).toFloat())
//            M.mapRect(clipCanvasRectF)
//            resetClipRectF()
            view.scrollTo(x, y)
            return true
        }
        return false
    }

    private fun resetClipRectF(){
        M.setTranslate(view.scrollX.toFloat(),view.scrollY.toFloat())
        M.preRotate(-imageRotate,clipCanvasRectF.centerX(),clipCanvasRectF.centerY())
        M.mapRect(clipCanvasRectF,imageClipController.clipRectF)
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

        val imageWinWidthOffset = windowRectF.width() * (1 - params.imageMatchPercent) / 2
        val imageWinHeightOffset = windowRectF.height() * (1 - params.imageMatchPercent) / 2
        imageWinRectF = RectF(imageWinWidthOffset,imageWinHeightOffset,
                windowRectF.width() - imageWinWidthOffset,windowRectF.height() - imageWinHeightOffset)

        M.reset()
        M.setRectToRect(imageRectF, imageWinRectF, Matrix.ScaleToFit.CENTER)
        M.mapRect(imageRectF) //通过矩阵变换矩形
        val imageMatrixArray = FloatArray(9)
        M.getValues(imageMatrixArray)
        val scaleX = imageMatrixArray[0]
        initScale = scaleX
        imageScale = initScale

        if(isClipMode()){
            imageClipController.resetClipWinRectF(imageWinRectF)
            imageClipController.clipRectF.set(imageRectF)
            imageClipController.clipTargetRectF.set(imageRectF)
//            resetClipRectF()
        }
        clipCanvasRectF.set(imageRectF)
        hasInitHoming = true
    }

    fun scale(scaleFactor: Float, px: Float, py: Float) {
        if (scaleFactor == 1f || scaleFactor * imageScale / initScale > PhotoEditView.SCALE_MAX) {
            return
        }
        M.apply { setScale(scaleFactor, scaleFactor, px, py) }.mapRect(imageRectF)
        if(!isClipMode()){
            M.apply { setScale(scaleFactor, scaleFactor, px, py) }.mapRect(clipCanvasRectF)
        }
        imageScale *= scaleFactor
    }

    fun rotate(rotate: Float) {
        if(isHoming()){
            return
        }
        imageTargetRotate = Math.round((imageRotate + rotate) / 90f) * 90f
        if(isClipMode()){
            imageClipController.reset(clipCanvasRectF,imageTargetRotate)
        }
        homing()
    }

    fun draw(canvas: Canvas, isSave: Boolean = false) {
        val rotateCenterRectF = if(isClipMode()) clipCanvasRectF else imageRectF
        canvas.save()
        canvas.rotate(imageRotate,rotateCenterRectF.centerX(),rotateCenterRectF.centerY())
        drawBitmap(canvas)
        if(!isSave){
            drawClipShadow(canvas)
        }
        canvas.restore()
        if(!isSave){
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
        if(isClipMode() && isSteady){
            shadowPath.reset()
            shadowPath.addRect(imageRectF,Path.Direction.CW)
            shadowPath.addRect(clipCanvasRectF,Path.Direction.CCW)
            canvas.drawPath(shadowPath,shadowPaint)
        }
    }

    private fun drawBitmap(canvas: Canvas) {
        val bitmap: Bitmap? = bitmap?:return
        canvas.drawBitmap(bitmap, null, imageRectF, null)
    }

    fun save(): Bitmap? {
        val saveRectF = if(isClipMode()) imageClipController.clipRectF else imageRectF
        val scale = 1 / imageScale
        val createBitmap = Bitmap.createBitmap(saveRectF.width().toInt(), saveRectF.height().toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(createBitmap)
        canvas.translate(-saveRectF.left , -saveRectF.top)
        draw(canvas,true)
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
        isSteady = false
        if(isClipMode()){
            imageClipController.onTouchDown(event)
        }
    }

    private fun onTouchUp(event: MotionEvent) {
        if(isClipMode()){
            imageClipController.onTouchUp(event,view.scrollX,view.scrollY )
        }
        homing()
    }

    fun onSteady(scrollX: Int, scrollY: Int) {
        isSteady = true
        if(isClipMode()){
            imageClipController.homing()
        }
    }


    fun isHoming(): Boolean {
        return homingAnimator?.isRunning ?: false
    }

    private fun stopHoming() {
        homingAnimator?.cancel()
    }

    /**
     * 图片归位
     */
    fun homing() {
        view.invalidate()
        stopHoming()
        startHoming()
    }

    private fun startHoming() {
        if (homingAnimator == null) {
            homingAnimator = ImgHomingAnimator().apply {
                addUpdateListener {
                    if(isClipMode()){
                        imageClipController.homing(it.animatedFraction)
                    }
                    applyHoming(it.animatedValue as ImgHomingValues)
                }

                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        super.onAnimationStart(animation)
                        isAnimatorCancelled = false
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        if(onHomingEnd()){
                            if(isClipMode()){
                                applyHoming(clip(view.scrollX,view.scrollY))
                            }
                        }
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                        super.onAnimationCancel(animation)
                        isAnimatorCancelled = true
                    }

                })
            }
        }
        homingAnimator?.setHomingValues(getStartHomingValues(), getEndHomingValues())
        homingAnimator?.start()
    }

    /**
     * 裁剪区域旋转回原始角度后形成新的裁剪区域，旋转中心发生变化，
     * 因此需要将视图窗口平移到新的旋转中心位置。
     */
    private fun clip(scrollX: Int,scrollY: Int): ImgHomingValues{
        val scrollClipRectF = imageClipController.getScrollClipRectF(scrollX, scrollY)
        M.setRotate(-imageRotate,clipCanvasRectF.centerX(),clipCanvasRectF.centerY())
        M.mapRect(clipCanvasRectF,scrollClipRectF)
        return ImgHomingValues(scrollX + clipCanvasRectF.centerX() - scrollClipRectF.centerX(),
                scrollY + clipCanvasRectF.centerY() - scrollClipRectF.centerY(),imageScale,imageRotate)
    }

    private fun onHomingEnd(): Boolean{
        if(isClipMode()){
            imageClipController.isResetting  = false
            imageClipController.isNeedHoming = false
            return !isAnimatorCancelled
        }
        return false
    }

    private fun getStartHomingValues(): ImgHomingValues {
        return ImgHomingValues(view.scrollX.toFloat(), view.scrollY.toFloat(), imageScale,imageRotate)
    }

    private fun getEndHomingValues(): ImgHomingValues {
        val homingValues = ImgHomingValues(view.scrollX.toFloat(), view.scrollY.toFloat(), imageScale,imageTargetRotate)
        if(isClipMode()){
            val clipTargetRectF = RectF(imageClipController.clipTargetRectF)
            clipTargetRectF.offset(view.scrollX.toFloat(),view.scrollY.toFloat())
            if(imageClipController.isResetting){
                val clipRectF = RectF()
                M.setRotate(imageTargetRotate,clipCanvasRectF.centerX(),clipCanvasRectF.centerY())
                M.mapRect(clipRectF,clipCanvasRectF)
                homingValues.concat(ImgHelper.fill(clipTargetRectF,clipRectF))
            }else{
                val rectF = RectF()
                if(imageClipController.isNeedHoming){
                    M.setRotate(imageTargetRotate - imageRotate,clipCanvasRectF.centerX(),clipCanvasRectF.centerY())
                    M.mapRect(rectF,imageClipController.getScrollClipRectF(view.scrollX,view.scrollY))
                    homingValues.concat(ImgHelper.getFitHomingValues(clipTargetRectF,rectF,clipCanvasRectF.centerX(),clipCanvasRectF.centerY()))
                }else{
                    M.setRotate(imageTargetRotate,clipCanvasRectF.centerX(),clipCanvasRectF.centerY())
                    M.mapRect(rectF,imageRectF)
                    homingValues.concat(ImgHelper.getFillHomingValues(clipTargetRectF,rectF,clipCanvasRectF.centerX(),clipCanvasRectF.centerY()))
                }
            }
        }else{
            val imageTargetRectF = RectF()
            M.setRotate(imageTargetRotate,imageRectF.centerX(),imageRectF.centerY())
            M.mapRect(imageTargetRectF,imageRectF)

            val imageCanvasWinRectF = RectF(imageWinRectF)
            imageCanvasWinRectF.offset(view.scrollX.toFloat(),view.scrollY.toFloat())
            homingValues.concat(ImgHelper.getFitHomingValues(imageCanvasWinRectF,imageTargetRectF))
        }
        return homingValues
    }

    private fun onTouchPath(event: MotionEvent): Boolean { return false }
    private fun onPathBegin(event: MotionEvent){}
    private fun onPathMove(event: MotionEvent){}
    private fun onPathDone(event: MotionEvent) {}
    fun recycle() {
        if(bitmap?.isRecycled == false){
            bitmap?.recycle()
        }
    }




}