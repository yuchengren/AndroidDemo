package com.ycr.lib.ui.view.ninegrid

import android.content.Context
import android.graphics.Canvas
import android.support.annotation.Nullable
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.ycr.lib.ui.R

/**
 * Created by yuchengren on 2018/12/27.
 */
class NineGridView: RecyclerView {
    var itemMaxCount = 9

    constructor(context: Context) : this(context, null)

    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.NineGridView, defStyleAttr, 0)
        typeArray.run {
//            itemMaxCount = getInt(R.styleable.NineGridView_item_max_count,9)
            recycle()
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }


}