package com.ycr.kernel.task

/**
 * Created by yuchengren on 2018/9/14.
 */
interface ITask{
    companion object {
        const val TASK_NAME_DEFAULT = "anonymous-task"
    }

    fun getTaskName(): String?

    /**
     * 是否是线程执行
     */
    fun isSerial(): Boolean

    /**
     * 重试
     */
    fun retry()

}