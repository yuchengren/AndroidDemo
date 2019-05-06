package com.ycr.kernel.task

/**
 * Created by yuchengren on 2018/9/14.
 */
interface ITaskPriority: Comparable<ITaskPriority>{
    companion object {
        const val PRIORITY_NORMAL = 1
        const val PRIORITY_UI = 32
        const val PRIORITY_HIGH = 256
    }

    fun priority(): Int
}