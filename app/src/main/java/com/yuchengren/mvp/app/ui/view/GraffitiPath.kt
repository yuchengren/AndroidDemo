package com.yuchengren.mvp.app.ui.view

import android.graphics.Matrix
import android.graphics.Path

/**
 * 涂鸦的路径
 * Created by yuchengren on 2018/11/2.
 */
class GraffitiPath(var path: Path = Path()){
    var id: Int = 0
    var pathData: StringBuilder = StringBuilder()

    var remarkStatus: String = RemarkStatus.MSG_NONE //当前批注的状态
    var msgOrder: Int = 0 //标注信息的序号
//    var startX: Float = 0f
//    var startY: Float = 0f
    var lastX: Float = 0f //轨迹最后一个点的横坐标
    var lastY: Float = 0f //轨迹最后一个点的纵坐标
//    var pointCount: Int = 0 //轨迹点的数目

    var pointerId: Int = Integer.MIN_VALUE //触摸点的id

    fun lineTo(x: Float,y: Float,matrix: Matrix? = null){
        val array = getMatrixMapPointArray(x,y,matrix)
        pathData.append("L${array[0].toInt()}$SIGN_COLON${array[1].toInt()}")
        path.lineTo(array[0],array[1])
        lastX = array[0]
        lastY = array[1]
//        pointCount++
    }

    fun moveTo(x: Float,y: Float,matrix: Matrix? = null){
        val array = getMatrixMapPointArray(x,y,matrix)
        pathData.append("M${array[0].toInt()}$SIGN_COLON${array[1].toInt()}")
        path.moveTo(array[0],array[1])
//        startX = array[0]
//        startY = array[1]
        lastX = array[0]
        lastY = array[1]
//        pointCount++
    }

    fun isEmpty(): Boolean{
        return path.isEmpty
    }

    fun reset() {
        path.reset()
        pointerId = Integer.MIN_VALUE
        pathData = StringBuilder()
        remarkStatus = RemarkStatus.MSG_NONE
        msgOrder = 0
        lastX = 0f
        lastY = 0f
//        pointCount = 0
    }

    fun isSamePointer(pointerId: Int): Boolean{
        return this.pointerId == pointerId
    }

    fun transform(matrix: Matrix){
        path.transform(matrix)
    }

    companion object {
        const val SIGN_COLON =","
        const val SIGN_BLANK = " "
        fun getMatrixMapPointArray(x: Float, y: Float, matrix: Matrix? = null): FloatArray{
            val array = floatArrayOf(x,y)
            matrix?.mapPoints(array)
            return array
        }
    }

    /**
     * 批注状态
     */
    interface RemarkStatus{
        companion object {
            const val MSG_NONE = "MSG_NONE" //未有批注信息 不可添加状态
            const val MSG_DONE = "MSG_DONE" //已批注信息
            const val MSG_ADD = "MSG_ADD" //未有批注信息 可添加状态
            const val MSG_EDITING = "MSG_EDITING" //批注信息编辑中
        }
    }
}
