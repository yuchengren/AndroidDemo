package com.ycr.kernel.task

/**
 * Created by yuchengren on 2018/12/3.
 */
class AsyncTaskInstance<R>(doBackground: ITaskBackground<R>, callback: ITaskCallback<R>?) : AbstractTask<R>(doBackground, callback) {

}