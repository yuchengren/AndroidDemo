package com.ycr.kernel.task

/**
 * Created by yuchengren on 2018/9/14.
 */
interface ITask{
    companion object {
        const val TASK_NAME_DEFAULT = "anonymous-task"
    }

    fun setTaskName(taskName: String?)

    fun taskName(): String?

    /**
     * 是否是 串行 执行（并行、串行）
     */
    fun isSerial(): Boolean

    /**
     * 重试
     */
    fun retry()

}