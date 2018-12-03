package com.ycr.kernel.task

/**
 * Created by yuchengren on 2018/8/6.
 */
interface ITaskCallback<R> {

    fun onBeforeCall(task: AbstractTask<R>)

    fun onAfterCall()

    fun onCompleted(data: R)

    fun onError(t: Throwable)

    fun onCancelled()


}