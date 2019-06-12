package com.ycr.lib.ui.recyclerview

import android.content.Context
import android.support.annotation.Nullable
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ycr.lib.ui.R
import com.ycr.lib.ui.view.gridimage.LinearItemDecoration

/**
 * created by yuchengren on 2019/5/15
 */
class LinearRecyclerView @JvmOverloads constructor(context: Context, @Nullable attrs: AttributeSet? = null,
      defStyleAttr: Int = 0) : BaseRecyclerView(context, attrs, defStyleAttr) {
    var orientation: Int = LinearLayout.VERTICAL
    var reverseLayout: Boolean = false

    var itemHorizontalSpacing = 0 //条目之间的横向间隔
    var itemVerticalSpacing = 0 //条目之间的纵向间隔

    var hasStartSpacing = true //是否朝向的起始位置有间隔
    var hasEndSpacing = false //是否朝向的末端位置有间隔

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.LinearRecyclerView, defStyleAttr, 0)
        typeArray.run {
            orientation = getInt(R.styleable.LinearRecyclerView_orientation, LinearLayout.VERTICAL)
            reverseLayout = getBoolean(R.styleable.LinearRecyclerView_reverseLayout, false)
            itemHorizontalSpacing = getDimensionPixelSize(R.styleable.LinearRecyclerView_itemHorizontalSpacing, 0)
            itemVerticalSpacing = getDimensionPixelSize(R.styleable.LinearRecyclerView_itemVerticalSpacing, 0)
            hasStartSpacing = getBoolean(R.styleable.LinearRecyclerView_hasStartSpacing, true)
            hasEndSpacing = getBoolean(R.styleable.LinearRecyclerView_hasEndSpacing, false)
            recycle()
        }
        layoutManager = LinearLayoutManager(context,orientation,reverseLayout)
        addItemDecoration(LinearItemDecoration(orientation == LinearLayout.VERTICAL,
                itemHorizontalSpacing,itemVerticalSpacing,hasStartSpacing,hasEndSpacing))
    }
}