package com.ycr.lib.ui.gridimage

import android.support.annotation.IdRes
import com.ycr.lib.ui.R

/**
 * Created by yuchengren on 2019/1/17.
 */
class GridImageMarkedRecyclerAdapter(data: MutableList<ImageMarkEntity>?, @IdRes itemResId: Int, var plusEnabled: Boolean = true)
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