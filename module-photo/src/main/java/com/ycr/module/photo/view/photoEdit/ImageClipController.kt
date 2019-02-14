package com.ycr.module.photo.view.photoEdit

import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent

/**
 * Created by yuchengren on 2019/1/25.
 */
class ImageClipController(private var clipColor: Int = 0xFFFFFF,private var clipCornerWidth: Int = 48,
                          private var clipCornerLineWidth: Int = 4,private var clipBorderLineWidth: Int = 1, private var clipSpanLineWidth: Int = 1,
                          private var clipRowSpans: Int = 3, private var clipColumnSpans: Int = 3) {
    var clipInitRectF: RectF = RectF()
    var clipRectF: RectF = RectF()
    var clipCornerPaint = Paint()
    var clipBorderLinePaint = Paint()
    var clipSpanLinePaint = Paint()
    var touchingAnchor: ClipAnchor? = null
    private var M = Matrix()

    init {
        initPaint()
    }

    private fun initPaint() {
        clipCornerPaint.run {
            style = Paint.Style.STROKE
            color = clipColor
            strokeWidth = clipCornerLineWidth.toFloat()
        }
        clipBorderLinePaint.run {
            style = Paint.Style.STROKE
            color = clipColor
            strokeWidth = clipBorderLineWidth.toFloat()
        }
        clipSpanLinePaint.run {
            style = Paint.Style.STROKE
            color = clipColor
            strokeWidth = clipSpanLineWidth.toFloat()
        }
    }

    fun drawClip(canvas: Canvas) {
        canvas.translate(clipRectF.left,clipRectF.top)
        drawSpanLines(canvas)
        drawCornerLines(canvas)
        canvas.translate(-clipRectF.left, -clipRectF.top)
        drawBorder(canvas)
    }

    private fun drawSpanLines(canvas: Canvas) {
        val baseSizes = Array(2){i ->
            val span = if(i == 0) clipColumnSpans  else clipRowSpans
            val size = if(i == 0) clipRectF.width()  else clipRectF.height()
            FloatArray(span + 1){
                j ->  j * (1f / span) * size
            }
        }
        val rowSpanPointCount = (clipRowSpans - 1) * 2
        val columnSpanPointCount = (clipColumnSpans - 1) * 2
        val spanLinePoints = FloatArray((rowSpanPointCount + columnSpanPointCount) * 2){ index ->
            val orientationIndex = index and 1
            var isXCoordinate: Boolean = orientationIndex == 0
            val sizeArray = baseSizes[orientationIndex]

            var isRowLinePoint = index <= rowSpanPointCount * 2 - 1
            var spanIndex = index
            if(!isRowLinePoint){
                spanIndex -= rowSpanPointCount * 2
            }
            val sizeIndex = if(isXCoordinate == isRowLinePoint){
                if(spanIndex % 4 / 2 == 0) 0 else sizeArray.size - 1
            }else{
                spanIndex / 4 + 1
            }
            sizeArray[sizeIndex]
        }
        canvas.drawLines(spanLinePoints,clipSpanLinePaint)
    }

    private fun drawCornerLines(canvas: Canvas) {
        val clipSizeArray = arrayOf(clipRectF.width(),clipRectF.height())
        val cornerLinePoints = FloatArray(32){index ->
            val orientationIndex = index and 1
            var isXCoordinate: Boolean = orientationIndex == 0
            val rowColumnIndex = index / 8
            val isRowLine = rowColumnIndex <= 1
            if(isXCoordinate == isRowLine){
                val lineIndex = index % 8 // 0 1 2 3 4 5 6 7
                val lineIndexDivide2 = lineIndex / 2 //  0 0 1 1 2 2 3 3
                val lineIndexDivide2Divide2 = lineIndexDivide2 / 2 // 0 0 0 0 1 1 1 1
                val lineIndexDivide2Mod2 = lineIndexDivide2 % 2 // 0 0 1 1 0 0 1 1
                val clipCornerWidthRatio = if (lineIndexDivide2 == 0 || lineIndexDivide2 == 3) 0 else lineIndexDivide2Mod2 * 2 - 1
                val clipCornerLineWidthRatio = if(isRowLine && (lineIndexDivide2 == 0 || lineIndexDivide2 == 3)) 0 else lineIndexDivide2Divide2 * 2 - 1
                clipCornerLineWidthRatio * clipCornerLineWidth +
                        clipCornerWidthRatio * clipCornerWidth +
                        lineIndexDivide2Divide2 * clipSizeArray[orientationIndex]
            }else{
                val rowColumnIndexMod2 = rowColumnIndex % 2 // 0是左上，1是右下
                (rowColumnIndexMod2 * 2 - 1) * clipCornerLineWidth / 2 +
                        clipSizeArray[orientationIndex] * (rowColumnIndex % 2)
            }
        }
        canvas.drawLines(cornerLinePoints,clipCornerPaint)
    }

    private fun drawBorder(canvas: Canvas) {
        if(clipBorderLineWidth <= 0){
            return
        }
        canvas.drawRect(clipRectF,clipBorderLinePaint)
    }

    fun onTouchDown(event: MotionEvent) {
        if(ClipAnchor.isOffsetRectFContainPoint(clipRectF, - clipCornerWidth.toFloat(),event.x,event.y) &&
                !ClipAnchor.isOffsetRectFContainPoint(clipRectF, clipCornerWidth.toFloat(),event.x,event.y)){
            val clipRectFArray = ClipAnchor.getOffsetRectFArray(clipRectF, 0f)
            val pointArray = floatArrayOf(event.x,event.y)
            var anchor = 0
            clipRectFArray.forEachIndexed { index, item ->
                if(Math.abs(item - pointArray[index shr 1]) < clipCornerWidth.toFloat()){
                    anchor = anchor or (1 shl index)
                }
            }
            touchingAnchor = ClipAnchor.valueOf(anchor)
        }
    }

    fun onTouchUp(event: MotionEvent, scrollX: Int, scrollY: Int) {
        if(touchingAnchor != null){
            touchingAnchor = null
        }
    }

    fun scroll(distanceX: Float, distanceY: Float): Boolean {
        if(touchingAnchor == null){
            return false
        }
        val minSize = clipCornerWidth
        val clipMinRectF = RectF(clipRectF.left + minSize,clipRectF.top + minSize,
                clipRectF.right - minSize,clipRectF.bottom - minSize)
        touchingAnchor?.move(clipRectF,clipInitRectF,clipMinRectF, -distanceX, -distanceY)
        return true
    }

    fun rotate(rotate: Float) {
        M.setRotate(rotate,clipRectF.centerX(),clipRectF.centerY())
        M.mapRect(clipRectF)
    }


}


