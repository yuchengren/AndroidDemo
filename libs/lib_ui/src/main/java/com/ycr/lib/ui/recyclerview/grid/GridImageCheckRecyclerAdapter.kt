package com.ycr.lib.ui.recyclerview.grid

import android.support.annotation.LayoutRes
import android.view.View
import android.widget.CheckBox
import com.ycr.lib.ui.R
import com.ycr.lib.ui.entity.ImageCheckEntity
import com.ycr.lib.ui.recyclerview.BaseImageRecyclerAdapter
import com.ycr.lib.ui.recyclerview.BaseRecyclerHolder

/**
 * Created by yuchengren on 2019/1/17.
 */
class GridImageCheckRecyclerAdapter(data: MutableList<ImageCheckEntity>?, @LayoutRes itemResId: Int, private var isMultiCheck: Boolean = false)
    : BaseImageRecyclerAdapter<ImageCheckEntity, BaseRecyclerHolder>(data,itemResId) {

    private var onItemCheckChangeListener: ((item: ImageCheckEntity, position: Int, checked: Boolean) -> Unit)? = null

    override fun convert(holder: BaseRecyclerHolder, position: Int, item: ImageCheckEntity?) {
        super.convert(holder, position, item)
        (holder.getView<View>(R.id.check) as? CheckBox)?.run {
            isChecked = item?.checked?:false
            setOnCheckedChangeListener { buttonView, isChecked ->
                if(!isMultiCheck && isChecked){
                    data?.forEachIndexed { index, imageCheckEntity ->
                        if(position != index){
                            imageCheckEntity.checked = false
                        }
                    }
                }
                item?.run {
                    checked = isChecked
                    onItemCheckChangeListener?.invoke(this,position,isChecked)
                }
                if(!isMultiCheck){
                    post { notifyDataSetChanged() }
                }
            }
        }
    }

    fun setOnItemCheckChangeListener(onItemCheckChangeListener: (item: ImageCheckEntity, position: Int, checked: Boolean) -> Unit){
        this.onItemCheckChangeListener = onItemCheckChangeListener
    }
}