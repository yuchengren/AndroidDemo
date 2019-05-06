package com.ycr.lib.ui.view.gridimage

/**
 * Created by yuchengren on 2019/1/17.
 */
interface OnItemCheckChangeListener<T> {
    fun onItemCheckChange(item: T,position: Int,checked: Boolean)
}