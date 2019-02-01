package com.ycr.lib.ui.view.gridimage

import android.support.annotation.IdRes

/**
 * Created by yuchengren on 2019/1/17.
 */
open class GridImageRecyclerAdapter(data: MutableList<String>?, @IdRes itemResId: Int): BaseImageRecyclerAdapter<String,BaseRecyclerHolder>(data,itemResId) {

    override fun convert(holder: BaseRecyclerHolder, position: Int, item: String?) {
        super.convert(holder, position, item)
    }
}