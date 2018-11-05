package com.ycr.kernel.task

/**
 * Created by yuchengren on 2018/8/6.
 */
interface ITaskBackground<R> {

    fun doInBackground(): R
}