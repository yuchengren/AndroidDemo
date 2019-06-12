package com.ycr.lib.ui.view.gridimage

import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View



/**
 * Created by yuchengren on 2019/1/2.
 */
class LinearItemDecoration(var isVertical: Boolean,var itemHorizontalSpacing: Int, var itemVerticalSpacing: Int,
                           var hasStartSpacing: Boolean,var hasEndSpacing: Boolean): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val count = parent.adapter?.itemCount?:return
        val position = parent.getChildAdapterPosition(view) // item position
        if (isVertical) {
            outRect.left = itemHorizontalSpacing
            outRect.right = itemHorizontalSpacing
            if((position != 0) || (position == 0 && hasStartSpacing)){
                outRect.top = itemVerticalSpacing
            }
            if((position == count - 1) && hasEndSpacing){
                outRect.bottom = itemVerticalSpacing
            }
        } else {
            outRect.top = itemVerticalSpacing
            outRect.bottom = itemVerticalSpacing
            if((position != 0) || (position == 0 && hasStartSpacing)){
                outRect.left = itemHorizontalSpacing
            }
            if((position == count - 1) && hasEndSpacing){
                outRect.right = itemHorizontalSpacing
            }
        }
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)
        
    }
}