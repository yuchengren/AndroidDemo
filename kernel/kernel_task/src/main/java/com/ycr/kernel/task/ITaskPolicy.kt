package com.ycr.kernel.task

/**
 * Created by yuchengren on 2018/9/14.
 */
interface ITaskPolicy {
    companion object {
        const val DISCARD_NEW = 0 //丢弃新任务
        const val CANCEL_OLD = 1 //取消老任务
        const val FORCE_SUBMIT = 2 //强制提交，任务可重复
    }

    /**
     * 任务去重策略
     */
    fun policy(): Int
}