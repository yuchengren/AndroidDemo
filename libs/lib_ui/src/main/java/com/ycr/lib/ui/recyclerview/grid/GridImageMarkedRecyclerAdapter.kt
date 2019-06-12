package com.ycr.lib.ui.recyclerview.grid

import android.support.annotation.LayoutRes
import com.ycr.lib.ui.R
import com.ycr.lib.ui.entity.ImageMarkEntity
import com.ycr.lib.ui.recyclerview.BaseImageRecyclerAdapter
import com.ycr.lib.ui.recyclerview.BaseRecyclerHolder
import com.ycr.lib.ui.widget.GridImageView

/**
 * Created by yuchengren on 2019/1/17.
 */
class GridImageMarkedRecyclerAdapter(data: MutableList<ImageMarkEntity>?, @LayoutRes itemResId: Int, var plusEnabled: Boolean = true)
    : BaseImageRecyclerAdapter<ImageMarkEntity, BaseRecyclerHolder>(data,itemResId) {

    private var maxItemCount = -1

    override fun getItem(position: Int): ImageMarkEntity? {
        if (isPlusItem(position)) {
            return null
        }
        return data?.get(position)
    }

    override fun getDataSourceSize(): Int {
        var itemCount = data?.size ?: 0
        if (plusEnabled && itemCount < maxItemCount) {
            itemCount += 1
        }
        return itemCount
    }

    private fun isPlusItem(position: Int): Boolean {
        return plusEnabled && data?.size ?: 0 < maxItemCount && position == getDataSourceSize() - 1
    }

    override fun convert(holder: BaseRecyclerHolder, position: Int, item: ImageMarkEntity?) {
        super.convert(holder, position, item)
        val gridImageView = holder.getView<GridImageView>(R.id.imageView)?:return
        if(item == null){

        }
        holder.getView<GridImageView>(R.id.imageView)?.run{
            strokeVisible = item?.isMarked?:false
        }


    }

}