package com.ycr.lib.ui.view.gridimage

import android.support.annotation.CallSuper
import android.support.annotation.IdRes
import android.widget.ImageView
import com.ycr.lib.ui.R

/**
 * Created by yuchengren on 2019/1/17.
 */
abstract class BaseImageRecyclerAdapter<T, VH : BaseRecyclerHolder>(data: MutableList<T>?, @IdRes itemResId: Int): BaseRecyclerAdapter<T,VH>(data,itemResId)  {

    private var onItemLoadImageListener: ((item: T,view: ImageView,position: Int) ->Unit)? = null

    @CallSuper
    override fun convert(holder: VH, position: Int, item: T?) {
        holder.getView<ImageView>(R.id.imageView)?.run {
            item?.let {
                onItemLoadImageListener?.invoke(it,this@run,position)
            }

            holder.addOnClickListener(R.id.imageView)
        }
    }

    fun setOnItemLoadImageListener(onItemLoadImageListener:(item: T,view: ImageView,position: Int) ->Unit){
        this.onItemLoadImageListener = onItemLoadImageListener
    }
}