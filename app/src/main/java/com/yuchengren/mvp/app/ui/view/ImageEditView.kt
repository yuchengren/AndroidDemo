package com.yuchengren.mvp.app.ui.view

import android.content.Context
import android.graphics.*
import android.support.annotation.Nullable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.util.*

/**
 * 图片编辑View
 * Created by yuchengren on 2018/11/1.
 */
class ImageEditView : View {
    companion object {
        /** 图片最大宽  */
        const val MAX_WIDTH = 1920
        /** 图片最大高  */
        const val MAX_HEIGHT = 1080
    }

    private var pathList: LinkedList<SVGPath> = LinkedList()
    private lateinit var paint: Paint
    private var currentSVGPath: SVGPath = SVGPath()
    private var bitmap: Bitmap? = null

    init {
        initPaint()
    }

    private fun initPaint() {
        paint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE //样式 描边
            strokeWidth = 5f //描边宽度
            color = Color.RED //颜色
            strokeCap = Paint.Cap.ROUND //起始端的线帽的类型 圆角
            strokeJoin = Paint.Join.ROUND //多线条连接拐角
            //PathEffect是用来控制绘制轮廓(线条)的方式
            //可以使用圆角来代替尖锐的角从而对基本图形的形状尖锐的边角进行平滑。
            pathEffect = CornerPathEffect(5f)
        }
    }

    constructor(context: Context) : super(context){

    }

    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs) {

    }

    constructor(context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }

    fun setImageBitmap(bitmap: Bitmap?){
        this.bitmap = bitmap
    }

    override fun onDraw(canvas: Canvas?) {
        if(canvas == null){
            return
        }
        drawBitmap(canvas)
        drawGraffiti(canvas)
    }

    private fun drawBitmap(canvas: Canvas) {
        val bitmap: Bitmap? = bitmap
        if(bitmap == null || bitmap.isRecycled){
            return
        }
        canvas.drawBitmap(bitmap,0f,0f,null)
    }

    private fun drawGraffiti(canvas: Canvas) {
        pathList.forEachIndexed { index, svgPath ->
            canvas.drawPath(svgPath.path,paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            null -> return super.onTouchEvent(event)
            MotionEvent.ACTION_DOWN -> {
                currentSVGPath = SVGPath()
                pathList.add(currentSVGPath)
                currentSVGPath.moveTo(event.x,event.y)
            }

            MotionEvent.ACTION_MOVE ->{
                currentSVGPath.lineTo(event.x,event.y)
                postInvalidate()
            }

            MotionEvent.ACTION_UP ->{
                currentSVGPath.lineTo(event.x,event.y)
                postInvalidate()
            }
        }
        return true
    }

    /**
     * 撤销
     */
    fun undo(){
        pathList.removeLast()
        postInvalidate()
    }

    /**
     * 清除
     */
    fun clear(){
        pathList.clear()
        postInvalidate()
    }


}