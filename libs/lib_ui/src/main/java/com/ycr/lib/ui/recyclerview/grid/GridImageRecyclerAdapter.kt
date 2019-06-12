package com.ycr.lib.ui.recyclerview.grid

import android.support.annotation.LayoutRes
import com.ycr.lib.ui.recyclerview.BaseImageRecyclerAdapter
import com.ycr.lib.ui.recyclerview.BaseRecyclerHolder

/**
 * Created by yuchengren on 2019/1/17.
 */
open class GridImageRecyclerAdapter(data: MutableList<String>?, @LayoutRes itemResId: Int): BaseImageRecyclerAdapter<String, BaseRecyclerHolder>(data,itemResId) {

    override fun convert(holder: BaseRecyclerHolder, position: Int, item: String?) {
        super.convert(holder, position, item)
    }
}