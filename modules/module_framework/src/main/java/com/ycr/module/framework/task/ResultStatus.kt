package com.ycr.module.framework.task

/**
 * Created by yuchengren on 2018/12/10.
 */
interface ResultStatus {
    companion object {
        const val EXCEPTION = "exception" //程序发生异常
        const val DATA_EMPTY = "data_empty" //数据为空
        const val DATA_ERROR = "data_error" //数据错误
        const val DATA_MISS= "data_miss" //数据解析过程中缺失
        const val PARSE_EXCEPTION = "parse_exception" //解析异常
        const val TASK_CANCELLED = "task_cancelled" //任务被取消
        const val NOT_LOGIN = "not_login" //未登录
        const val LOGIN_AT_OTHER_CLIENT = "loginAtOtherClient" //在其他终端登录
        const val LOGIN_SESSION_INVALID= "loginSessionInvalid" //登录信息失效
        const val SERVER_ERROR= "serverError" //服务器异常

    }
}