package com.ycr.lib.ui.recyclerview.grid

import android.content.Context
import android.support.annotation.Nullable
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import com.ycr.lib.ui.R
import com.ycr.lib.ui.recyclerview.BaseRecyclerView


/**
 * Created by yuchengren on 2019/1/17.
 */
class GridRecyclerView: BaseRecyclerView {
    var columnsNum = 3 //列数
    var itemHorizontalSpacing = 0 //条目之间的横向间隔
    var itemVerticalSpacing = 0 //条目之间的纵向间隔
    var hasEdge = false //是否有边缘间隔

    constructor(context: Context) : this(context, null)

    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.GridRecyclerView, defStyleAttr, 0)
        typeArray.run {
            columnsNum = getInt(R.styleable.GridRecyclerView_columnsNum, 3)
            itemHorizontalSpacing = getDimensionPixelSize(R.styleable.GridRecyclerView_itemHorizontalSpacing, 0)
            itemVerticalSpacing = getDimensionPixelSize(R.styleable.GridRecyclerView_itemVerticalSpacing, 0)
            hasEdge = getBoolean(R.styleable.GridRecyclerView_hasEdge, false)
            recycle()
        }
        layoutManager = GridLayoutManager(context, columnsNum)
        addItemDecoration(GridItemDecoration(itemHorizontalSpacing, itemVerticalSpacing, columnsNum, hasEdge))
    }
}