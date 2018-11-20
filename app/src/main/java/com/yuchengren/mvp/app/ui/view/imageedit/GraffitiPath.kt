package com.yuchengren.mvp.app.ui.view.imageedit

import android.graphics.Matrix
import android.graphics.Path
import android.graphics.PointF

/**
 * 涂鸦的路径
 * Created by yuchengren on 2018/11/2.
 */
class GraffitiPath(var path: Path = Path()){
    var id: Int = 0
    var pathData: StringBuilder = StringBuilder()

    var remarkStatus: String = RemarkStatus.MSG_NONE //当前批注的状态
    var msgOrder: Int = 0 //标注信息的序号
    var lastX: Float = 0f //轨迹最后一个点的横坐标
    var lastY: Float = 0f //轨迹最后一个点的纵坐标
//    var pointCount: Int = 0 //轨迹点的数目

    var pointerId: Int = Integer.MIN_VALUE //触摸点的id

    fun lineTo(x: Float,y: Float,matrix: Matrix? = null){
        val array = getMatrixMapPointArray(x, y, matrix)
        path.lineTo(array[0],array[1])
        lastX = array[0]
        lastY = array[1]
        //过滤 位置相同的点
        if(lastX.toInt() != array[0].toInt() || lastY.toInt() != array[1].toInt()){
            pathData.append("l${array[0].toInt() - lastX.toInt()}$SIGN_COLON${array[1].toInt()-lastY.toInt()}")
        }
//        pointCount++
    }

    fun moveTo(x: Float,y: Float,matrix: Matrix? = null){
        val array = getMatrixMapPointArray(x, y, matrix)
        path.moveTo(array[0],array[1])
        pathData.append("m${array[0].toInt()}$SIGN_COLON${array[1].toInt()}")
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

    companion object {
        const val SIGN_COLON =","
        const val SIGN_BLANK = " "
        fun getMatrixMapPointArray(x: Float, y: Float, matrix: Matrix? = null): FloatArray{
            val array = floatArrayOf(x,y)
            matrix?.mapPoints(array)
            return array
        }

        fun parseSVGStringToPath(svgStr: String): Path{
            val path = Path().apply { fillType = Path.FillType.WINDING }
            val svgCmdIndexList = getSVGCmdIndexList(svgStr)
            var lastPoint = PointF()
            svgCmdIndexList.forEachIndexed { index, position ->
                val svgCmdChar = svgStr[position]
                when(svgCmdChar){
                    'M','m' -> {
                        val variables = findCmdVariables(svgStr, svgCmdIndexList, index)
                        if(variables.size < 2){
                            return@forEachIndexed
                        }
                        lastPoint.set(variables[0].toFloat(),variables[1].toFloat())
                        path.moveTo(lastPoint.x,lastPoint.y)
                    }
                    'L','l' ->{
                        val variables = findCmdVariables(svgStr, svgCmdIndexList, index)
                        if(variables.size < 2){
                            return@forEachIndexed
                        }
                        val x = if(svgCmdChar == 'L') variables[0].toFloat() else variables[0].toFloat() + lastPoint.x
                        val y = if(svgCmdChar == 'L') variables[1].toFloat() else variables[1].toFloat() + lastPoint.y
                        lastPoint.set(x,y)
                        path.lineTo(lastPoint.x,lastPoint.y)
                    }
                    //基于上个坐标在水平方向上划线，因此y轴不变
                    'H','h' ->{
                        val variables = findCmdVariables(svgStr, svgCmdIndexList, index)
                        if(variables.isEmpty()){
                            return@forEachIndexed
                        }
                        val x = if(svgCmdChar == 'H') variables[0].toFloat() else variables[0].toFloat() + lastPoint.x
                        lastPoint.set(x,lastPoint.y)
                        path.lineTo(lastPoint.x,lastPoint.y)
                    }
                    //基于上个坐标在水平方向上划线，因此x轴不变
                    'V','v' ->{
                        val variables = findCmdVariables(svgStr, svgCmdIndexList, index)
                        if(variables.isEmpty()){
                            return@forEachIndexed
                        }
                        val y = if(svgCmdChar == 'V') variables[1].toFloat() else variables[1].toFloat() + lastPoint.y
                        lastPoint.set(lastPoint.x,y)
                        path.lineTo(lastPoint.x,lastPoint.y)
                    }
                    //3次贝塞尔曲线
                    'C','c' ->{
                        val variables = findCmdVariables(svgStr, svgCmdIndexList, index)
                        if(variables.size < 6){
                            return@forEachIndexed
                        }
                        val x1 = if(svgCmdChar == 'C') variables[0].toFloat() else variables[0].toFloat() + lastPoint.x
                        val y1 = if(svgCmdChar == 'C') variables[1].toFloat() else variables[1].toFloat() + lastPoint.y
                        val x2 = if(svgCmdChar == 'C') variables[2].toFloat() else variables[2].toFloat() + lastPoint.x
                        val y2 = if(svgCmdChar == 'C') variables[3].toFloat() else variables[3].toFloat() + lastPoint.y
                        val x3 = if(svgCmdChar == 'C') variables[4].toFloat() else variables[4].toFloat() + lastPoint.x
                        val y3 = if(svgCmdChar == 'C') variables[5].toFloat() else variables[5].toFloat() + lastPoint.y
                        lastPoint.set(x3,y3)
                        path.cubicTo(x1,y1,x2,y2,x3,y3)
                    }
                    //一般S会跟在C或是S命令后面使用，用前一个点做起始控制点
                    'S','s' ->{
                        val variables = findCmdVariables(svgStr, svgCmdIndexList, index)
                        if(variables.size < 4){
                            return@forEachIndexed
                        }
                        val x2 = if(svgCmdChar == 'S') variables[0].toFloat() else variables[0].toFloat() + lastPoint.x
                        val y2 = if(svgCmdChar == 'S') variables[1].toFloat() else variables[1].toFloat() + lastPoint.y
                        val x3 = if(svgCmdChar == 'S') variables[2].toFloat() else variables[2].toFloat() + lastPoint.x
                        val y3 = if(svgCmdChar == 'S') variables[3].toFloat() else variables[3].toFloat() + lastPoint.y
                        path.cubicTo(lastPoint.x,lastPoint.y,x2,y2,x3,y3)
                        lastPoint.set(x3,y3)
                    }
                    //二次贝塞尔曲线
                    'Q','q'->{
                        val variables = findCmdVariables(svgStr, svgCmdIndexList, index)
                        if(variables.size < 4){
                            return@forEachIndexed
                        }
                        val x1 = if(svgCmdChar == 'Q') variables[0].toFloat() else variables[0].toFloat() + lastPoint.x
                        val y1 = if(svgCmdChar == 'Q') variables[1].toFloat() else variables[1].toFloat() + lastPoint.y
                        val x2 = if(svgCmdChar == 'Q') variables[2].toFloat() else variables[2].toFloat() + lastPoint.x
                        val y2 = if(svgCmdChar == 'Q') variables[3].toFloat() else variables[3].toFloat() + lastPoint.y
                        path.quadTo(x1,y1,x2,y2)
                        lastPoint.set(x2,y2)
                    }
                    //T命令会跟在Q后面使用，用Q的结束点做起始点
                    'T','t'->{
                        val variables = findCmdVariables(svgStr, svgCmdIndexList, index)
                        if(variables.size < 2){
                            return@forEachIndexed
                        }
                        val x2 = if(svgCmdChar == 'T') variables[0].toFloat() else variables[0].toFloat() + lastPoint.x
                        val y2 = if(svgCmdChar == 'T') variables[1].toFloat() else variables[1].toFloat() + lastPoint.y
                        path.quadTo(lastPoint.x,lastPoint.y,x2,y2)
                        lastPoint.set(x2,y2)
                    }
                    //画弧 待研究
                    'A','a'->{
                        val variables = findCmdVariables(svgStr, svgCmdIndexList, index)
                    }
                    'Z','z' -> path.close()
                }
            }
            return path
        }

        private fun findCmdVariables(svgStr: String,svgCmdIndexList: List<Int>,cmdIndex: Int): List<String>{
            val cmdEndPosition = if(cmdIndex < svgCmdIndexList.size - 1) svgCmdIndexList[cmdIndex + 1] else svgStr.length
            return svgStr.substring(svgCmdIndexList[cmdIndex] + 1, cmdEndPosition).split(SIGN_COLON)
        }

        private fun getSVGCmdIndexList(svgStr: String): List<Int>{
            val list = ArrayList<Int>()
            svgStr.forEachIndexed { index, c ->
                if(c in 'A'..'Z' || c in 'a'..'z'){
                    list.add(index)
                }
            }
            return list
        }
    }

}
