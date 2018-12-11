package com.ycr.kernel.union.task

import com.ycr.kernel.task.*

/**
 * Created by yuchengren on 2018/12/3.
 */
object TaskHelper {

    fun <R> buildTask(groupName: String?, taskName: String?, doBackground: ITaskBackground<R>, callback: ITaskCallback<R>?): AsyncTask<R>{
        return AsyncTask<R>(doBackground,callback).apply {
            setGroupName(groupName)
            setTaskName(taskName)
        }
    }

    fun <R> submitTask(groupName: String?, taskName: String?, doBackground: ITaskBackground<R>, callback: ITaskCallback<R>?): AsyncTask<R>{
        return buildTask(groupName, taskName, doBackground, callback).apply {
            setSerial(false)
            setPolicy(ITaskPolicy.DISCARD_NEW)
            TaskScheduler.submit(this)
        }
    }
}