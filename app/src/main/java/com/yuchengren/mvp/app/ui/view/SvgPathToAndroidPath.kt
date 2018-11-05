package com.yuchengren.mvp.app.ui.view

/**
 * Created by yuchengren on 2018/11/2.
 */
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.util.Log

import java.util.ArrayList

/**
 * Created by Shuxin on 2016/8/3.
 */
class SvgPathToAndroidPath {
    private var svgPathLenght = 0
    private var svgPath: String? = null
    private var mIndex: Int = 0
    private val cmdPositions = ArrayList<Int>()
    /**
     * M x,y
     * L x,y
     * H x
     * V y
     * C x1,y1,x2,y2,x,y
     * Q x1,y1,x,y
     * S x2,y2,x,y
     * T x,y
     */
    fun parser(svgPath: String): Path {
        this.svgPath = svgPath
        svgPathLenght = svgPath.length
        mIndex = 0
        val lPath = Path()
        lPath.fillType = Path.FillType.WINDING
        //记录最后一个操作点
        val lastPoint = PointF()
        findCommand()
        for (i in cmdPositions.indices) {
            val index = cmdPositions[i]
            when (svgPath[index]) {
                'm', 'M' -> {
                    val ps = findPoints(i)
                    lastPoint.set(java.lang.Float.parseFloat(ps[0]), java.lang.Float.parseFloat(ps[1]))
                    lPath.moveTo(lastPoint.x, lastPoint.y)
                }
                'l', 'L' -> {
                    val ps = findPoints(i)
                    lastPoint.set(java.lang.Float.parseFloat(ps[0]), java.lang.Float.parseFloat(ps[1]))
                    lPath.lineTo(lastPoint.x, lastPoint.y)
                }
                'h', 'H' -> {//基于上个坐标在水平方向上划线，因此y轴不变
                    val ps = findPoints(i)
                    lastPoint.set(java.lang.Float.parseFloat(ps[0]), lastPoint.y)
                    lPath.lineTo(lastPoint.x, lastPoint.y)
                }
                'v', 'V' -> {//基于上个坐标在水平方向上划线，因此x轴不变
                    val ps = findPoints(i)
                    lastPoint.set(lastPoint.x, java.lang.Float.parseFloat(ps[0]))
                    lPath.lineTo(lastPoint.x, lastPoint.y)
                }
                'c', 'C' -> {//3次贝塞尔曲线
                    val ps = findPoints(i)
                    lastPoint.set(java.lang.Float.parseFloat(ps[4]), java.lang.Float.parseFloat(ps[5]))
                    lPath.cubicTo(java.lang.Float.parseFloat(ps[0]), java.lang.Float.parseFloat(ps[1]), java.lang.Float.parseFloat(ps[2]), java.lang.Float.parseFloat(ps[3]), java.lang.Float.parseFloat(ps[4]), java.lang.Float.parseFloat(ps[5]))
                }
                's', 'S' -> {//一般S会跟在C或是S命令后面使用，用前一个点做起始控制点
                    val ps = findPoints(i)
                    lPath.cubicTo(lastPoint.x, lastPoint.y, java.lang.Float.parseFloat(ps[0]), java.lang.Float.parseFloat(ps[1]), java.lang.Float.parseFloat(ps[2]), java.lang.Float.parseFloat(ps[3]))
                    lastPoint.set(java.lang.Float.parseFloat(ps[2]), java.lang.Float.parseFloat(ps[3]))
                }
                'q', 'Q' -> {//二次贝塞尔曲线
                    val ps = findPoints(i)
                    lastPoint.set(java.lang.Float.parseFloat(ps[2]), java.lang.Float.parseFloat(ps[3]))
                    lPath.quadTo(java.lang.Float.parseFloat(ps[0]), java.lang.Float.parseFloat(ps[1]), java.lang.Float.parseFloat(ps[2]), java.lang.Float.parseFloat(ps[3]))
                }
                't', 'T' -> {//T命令会跟在Q后面使用，用Q的结束点做起始点
                    val ps = findPoints(i)
                    lPath.quadTo(lastPoint.x, lastPoint.y, java.lang.Float.parseFloat(ps[0]), java.lang.Float.parseFloat(ps[1]))
                    lastPoint.set(java.lang.Float.parseFloat(ps[0]), java.lang.Float.parseFloat(ps[1]))
                }
                'a', 'A' -> {//画弧
                }
                'z', 'Z' -> {//结束
                    lPath.close()
                }
            }
        }
        return lPath
    }

    private fun findPoints(cmdIndexInPosition: Int): Array<String> {
        val cmdIndex = cmdPositions[cmdIndexInPosition]
        val pointString = svgPath!!.substring(cmdIndex + 1, cmdPositions[cmdIndexInPosition + 1])
        return pointString.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    private fun findCommand() {
        cmdPositions.clear()
        while (mIndex < svgPathLenght) {
            val c = svgPath!![mIndex]
            if ('A' <= c && c <= 'Z') {
                cmdPositions.add(mIndex)
            } else if ('a' <= c && c <= 'z') {
                cmdPositions.add(mIndex)
            }
            ++mIndex
        }
    }
}
