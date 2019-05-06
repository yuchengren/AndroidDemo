package com.ycr.kernel.union.task

/**
 * Created by yuchengren on 2018/12/7.
 */
interface IResultCallback<T> {
    fun onSuccess(result: T)
    fun onFailure(result: T)
}