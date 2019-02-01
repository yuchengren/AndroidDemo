package com.ycr.module.photo.view.photoEdit

import android.graphics.RectF

/**
 * Created by yuchengren on 2019/1/30.
 */
enum class ClipAnchor(var anchor: Int) {
    LEFT(1),
    RIGHT(2),
    TOP(4),
    BOTTOM(8),
    LEFT_TOP(5),
    RIGHT_TOP(6),
    LEFT_BOTTOM(9),
    RIGHT_BOTTOM(10);
    companion object {
        fun isOffsetRectFContainPoint(rectF: RectF,offset: Float,x: Float,y: Float): Boolean{
            return rectF.left + offset < x && x < rectF.right - offset &&
                    rectF.top + offset < y && y < rectF.bottom - offset
        }

        fun getOffsetRectFArray(rectF: RectF,offset: Float): FloatArray{
            return floatArrayOf(rectF.left + offset,rectF.right - offset, rectF.top + offset,rectF.bottom)
        }

        fun valueOf(anchor: Int): ClipAnchor?{
            for (item in values()) {
                if(item.anchor == anchor){
                    return item
                }
            }
            return null
        }

        fun revise(num: Float,max: Float,min: Float): Float{
            return Math.min(Math.max(num,min),max)
        }
    }

    fun move(clipRectF: RectF,maxRectF: RectF,minRectF: RectF,dx: Float,dy: Float){
        val clipRectFloatArray = getOffsetRectFArray(clipRectF,0f)
        val maxRectFloatArray = getOffsetRectFArray(maxRectF,0f)
        val minRectFloatArray = getOffsetRectFArray(minRectF,0f)
        val dxy = floatArrayOf(dx,dy)
        clipRectFloatArray.forEachIndexed { index, item ->
            if((1 shl index) and anchor == 0){
                return@forEachIndexed
            }
            val pn = 1 - 2 * (index and 1)
            clipRectFloatArray[index] = pn * revise(pn * (item + dxy[index shr 1]),
                    pn * maxRectFloatArray[index],pn * minRectFloatArray[index])
        }
        clipRectF.set(clipRectFloatArray[0],clipRectFloatArray[2],clipRectFloatArray[1],clipRectFloatArray[3])
    }
}