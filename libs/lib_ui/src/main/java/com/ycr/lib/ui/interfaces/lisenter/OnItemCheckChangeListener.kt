package com.ycr.lib.ui.interfaces.lisenter

/**
 * Created by yuchengren on 2019/1/17.
 */
interface OnItemCheckChangeListener<T> {
    fun onItemCheckChange(item: T,position: Int,checked: Boolean)
}