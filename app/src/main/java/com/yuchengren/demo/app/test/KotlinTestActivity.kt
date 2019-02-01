package com.yuchengren.demo.app.test

import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ycr.kernel.log.LogHelper
import com.ycr.module.base.BaseActivity
import com.yuchengren.demo.R

/**
 * Created by yuchengren on 2019/1/28.
 */
class KotlinTestActivity: BaseActivity() {
    private var clipRowSpans: Int = 3
    private var clipColumnSpans: Int = 3
    private var clipCornerWidth: Int = 10
    private var clipCornerLineWidth: Int = 1
    private val clipRectF = RectF(0f,0f,150f,90f)
    override fun getRootLayoutResId(): Int {
        return R.layout.activity_kotlin_test
    }

    override fun afterBindView(rootView: View?, savedInstanceState: Bundle?) {
        super.afterBindView(rootView, savedInstanceState)
        drawSpanLines()
        drawCornerLines()
//        spanLinePoints.forEachIndexed { index, fl ->
//            Log.e("spanLinePoints","index = $index,zuobiao = $fl")
//        }

    }

    private fun drawSpanLines() {
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

        spanLinePoints.forEachIndexed { index, fl ->
            Log.e("spanLinePoints","index = $index,zuobiao = $fl")
        }
    }

    private fun drawCornerLines() {
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
                (lineIndexDivide2Divide2 * 2 - 1) * clipCornerLineWidth +
                        clipCornerWidthRatio * clipCornerWidth +
                        lineIndexDivide2Divide2 * clipSizeArray[orientationIndex]
            }else{
                val rowColumnIndexMod2 = rowColumnIndex % 2 // 0是左上，1是右下
                (rowColumnIndexMod2 * 2 - 1) * clipCornerLineWidth +
                        clipSizeArray[orientationIndex] * (rowColumnIndex % 2)
            }
        }
//        canvas.drawLines(cornerLinePoints,clipCornerPaint)
    }
}