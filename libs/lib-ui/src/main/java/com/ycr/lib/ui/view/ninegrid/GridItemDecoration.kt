package com.ycr.lib.ui.view.ninegrid

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View



/**
 * Created by yuchengren on 2019/1/2.
 */
class GridItemDecoration(var itemHorizontalSpacing: Int,var itemVerticalSpacing: Int,var spanCount: Int,var hasEdge: Boolean): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if(spanCount == 0){
            return
        }
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        if (hasEdge) {
            outRect.left = itemHorizontalSpacing - column * itemHorizontalSpacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * itemHorizontalSpacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
            if (position < spanCount) { // top edge
                outRect.top = itemVerticalSpacing
            }
            outRect.bottom = itemVerticalSpacing // item bottom
        } else {
            outRect.left = column * itemHorizontalSpacing / spanCount // column * ((1f / spanCount) * spacing)
            outRect.right = itemHorizontalSpacing - (column + 1) * itemHorizontalSpacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = itemVerticalSpacing // item top
            }
        }
    }
}