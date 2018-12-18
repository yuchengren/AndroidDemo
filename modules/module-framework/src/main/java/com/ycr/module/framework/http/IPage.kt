package com.ycr.module.framework.http

/**
 * Created by yuchengren on 2018/12/18.
 */
interface IPage<T> {
    companion object {
        const val FIRST_PAGE_INDEX = 0
    }
    fun pageList(): List<T>?
    fun hasNext(): Boolean
}