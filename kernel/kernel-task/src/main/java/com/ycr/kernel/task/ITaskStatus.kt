package com.ycr.kernel.task

/**
 * Created by yuchengren on 2018/9/7.
 */
interface ITaskStatus {
    companion object {
        const val NEW = 0
        const val READY = 1
        const val WAIT = 2
        const val RUNNING = 3
        const val OVER = 4
        const val CANCLE = 5
    }
    fun setStatus(status: Int)
    fun getStatus(): Int
}




