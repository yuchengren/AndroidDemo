package com.ycr.module.framework.task

/**
 * Created by yuchengren on 2018/12/10.
 */
interface ResultStatus {
    companion object {
        const val EXCEPTION = "exception" //程序发生异常
        const val DATA_EMPTY = "data_empty" //数据为空
        const val PARSE_EXCEPTION = "parse_exception" //解析异常
        const val TASK_CANCELLED = "task_cancelled" //任务被取消
    }
}