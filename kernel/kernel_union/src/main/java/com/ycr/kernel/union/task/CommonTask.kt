package com.ycr.kernel.union.task

import com.ycr.kernel.task.AbstractTask
import com.ycr.kernel.task.ITaskBackground
import com.ycr.kernel.task.ITaskCallback

/**
 * created by yuchengren on 2019/5/28
 */
abstract class CommonTask<R>: ITaskBackground<R>, ITaskCallback<R> {

    override fun onBeforeCall(task: AbstractTask<R>) {
    }

    override fun onAfterCall() {
    }

    override fun onCompleted(data: R) {
    }

    override fun onError(tr: Throwable) {
    }

    override fun onCancelled() {
    }
}