package com.ycr.kernel.task

/**
 * Created by yuchengren on 2018/9/7.
 */
interface ITaskStatus {
    companion object {
        const val NEW = 0 //新建状态
        const val READY = 1 //就绪
        const val WAIT = 2 //等待或阻塞
        const val RUNNING = 3 //运行
        const val OVER = 4 //结束
        const val CANCEL = 5 //取消
    }
    fun status(): Int
}




