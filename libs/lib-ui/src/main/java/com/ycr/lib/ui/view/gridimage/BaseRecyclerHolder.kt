package com.ycr.lib.ui.view.gridimage

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import java.util.*

/**
 * Created by yuchengren on 2019/1/17.
 */
class BaseRecyclerHolder(var convertView: View): RecyclerView.ViewHolder(convertView) {
    val views: SparseArray<View> = SparseArray()
    val childClickViewIds = LinkedList<Int>()
    val childLongClickViewIds = LinkedList<Int>()
    val childHoverViewIds = LinkedList<Int>()


    lateinit var adapter: BaseRecyclerAdapter<*,*>

    fun <T: View?> getView(@IdRes viewResId: Int): T?{
        var view: View? = views.get(viewResId)
        if(view == null){
            view = convertView.findViewById(viewResId)
            views.put(viewResId,view)
        }
        return view as? T
    }

    fun addOnClickListener(@IdRes viewResId: Int){
        childClickViewIds.add(viewResId)
        getView<View>(viewResId)?.run {
            if(!isClickable){
                isClickable = true
            }
            setOnClickListener {
                adapter.onItemChildClickListener?.onItemChildClick(adapter,this,getClickPosition())
            }
        }
    }

    fun addOnLongClickListener(@IdRes viewResId: Int){
        childLongClickViewIds.add(viewResId)
        getView<View>(viewResId)?.run {
            if(!isLongClickable){
                isLongClickable = true
            }
            setOnLongClickListener{
                adapter.onItemChildLongClickListener?.onItemChildLongClick(adapter,this,getClickPosition())?:false
            }
        }
    }

    fun addOnHoverListener(@IdRes viewResId: Int){
        childHoverViewIds.add(viewResId)
        getView<View>(viewResId)?.run {
            setOnHoverListener { v, event ->
                when (event.action){
                    MotionEvent.ACTION_HOVER_ENTER -> adapter.onItemChildHoverActionListener?.onHoverEnter(adapter,this,getClickPosition(),event)?: false
                    MotionEvent.ACTION_HOVER_MOVE ->  adapter.onItemChildHoverActionListener?.onHoverMove(adapter,this,getClickPosition(),event)?: false
                    MotionEvent.ACTION_HOVER_EXIT ->  adapter.onItemChildHoverActionListener?.onHoverExit(adapter,this,getClickPosition(),event)?: false
                    else -> false
                }
            }
        }
    }

    private fun getClickPosition(): Int {
        return if (layoutPosition >= adapter.getHeaderLayoutCount()) {
            layoutPosition - adapter.getHeaderLayoutCount()
        } else 0
    }
}