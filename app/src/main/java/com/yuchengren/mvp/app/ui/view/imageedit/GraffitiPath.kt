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
    //    var startX: Float = 0f
//    var startY: Float = 0f
    var lastX: Float = 0f //轨迹最后一个点的横坐标
    var lastY: Float = 0f //轨迹最后一个点的纵坐标
//    var pointCount: Int = 0 //轨迹点的数目

    var pointerId: Int = Integer.MIN_VALUE //触摸点的id

    fun lineTo(x: Float,y: Float,matrix: Matrix? = null){
        val array = getMatrixMapPointArray(x, y, matrix)
        pathData.append("L${array[0].toInt()}$SIGN_COLON${array[1].toInt()}")
        path.lineTo(array[0],array[1])
        lastX = array[0]
        lastY = array[1]
//        pointCount++
    }

    fun moveTo(x: Float,y: Float,matrix: Matrix? = null){
        val array = getMatrixMapPointArray(x, y, matrix)
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
                when(svgStr[position]){
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
                        lastPoint.set(variables[0].toFloat(),variables[1].toFloat())
                        path.lineTo(lastPoint.x,lastPoint.y)
                    }
                    //基于上个坐标在水平方向上划线，因此y轴不变
                    'H','h' ->{
                        val variables = findCmdVariables(svgStr, svgCmdIndexList, index)
                        if(variables.isEmpty()){
                            return@forEachIndexed
                        }
                        lastPoint.set(variables[0].toFloat(),lastPoint.y)
                        path.lineTo(lastPoint.x,lastPoint.y)
                    }
                    //基于上个坐标在水平方向上划线，因此x轴不变
                    'V','v' ->{
                        val variables = findCmdVariables(svgStr, svgCmdIndexList, index)
                        if(variables.isEmpty()){
                            return@forEachIndexed
                        }
                        lastPoint.set(lastPoint.x,variables[0].toFloat())
                        path.lineTo(lastPoint.x,lastPoint.y)
                    }
                    //3次贝塞尔曲线
                    'C','c' ->{
                        val variables = findCmdVariables(svgStr, svgCmdIndexList, index)
                        if(variables.size < 6){
                            return@forEachIndexed
                        }
                        lastPoint.set(variables[4].toFloat(),variables[5].toFloat())
                        path.cubicTo(variables[0].toFloat(),variables[1].toFloat(),variables[2].toFloat(),variables[3].toFloat(),
                                variables[4].toFloat(),variables[5].toFloat())
                    }
                    //一般S会跟在C或是S命令后面使用，用前一个点做起始控制点
                    'S','s' ->{
                        val variables = findCmdVariables(svgStr, svgCmdIndexList, index)
                        if(variables.size < 4){
                            return@forEachIndexed
                        }
                        path.cubicTo(lastPoint.x,lastPoint.y,variables[0].toFloat(),variables[1].toFloat(),
                                variables[2].toFloat(),variables[3].toFloat())
                        lastPoint.set(variables[2].toFloat(),variables[3].toFloat())
                    }
                    //二次贝塞尔曲线
                    'Q','q'->{
                        val variables = findCmdVariables(svgStr, svgCmdIndexList, index)
                        if(variables.size < 4){
                            return@forEachIndexed
                        }
                        path.quadTo(variables[0].toFloat(),variables[1].toFloat(),variables[2].toFloat(),variables[3].toFloat())
                        lastPoint.set(variables[2].toFloat(),variables[3].toFloat())
                    }
                    //T命令会跟在Q后面使用，用Q的结束点做起始点
                    'T','t'->{
                        val variables = findCmdVariables(svgStr, svgCmdIndexList, index)
                        if(variables.size < 2){
                            return@forEachIndexed
                        }
                        path.quadTo(lastPoint.x,lastPoint.y,variables[0].toFloat(),variables[1].toFloat())
                        lastPoint.set(variables[0].toFloat(),variables[1].toFloat())
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
