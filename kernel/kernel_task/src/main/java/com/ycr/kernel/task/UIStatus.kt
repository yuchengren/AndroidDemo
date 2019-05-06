package com.ycr.kernel.task

/**
 * Created by yuchengren on 2018/11/30.
 */
interface UIStatus {
    companion object {
        /**
         * UI开始状态 恢复被降级的任务
         */
        const val START = 1
        /**
         * UI停止状态 降级其下的任务
         */
        const val STOP = 2
        /**
         * UI销毁 取消由其发起的任务
         */
        const val DESTROY = 3
    }
}