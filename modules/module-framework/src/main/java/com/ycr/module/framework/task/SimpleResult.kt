package com.ycr.module.framework.task

import com.ycr.kernel.union.task.Result

/**
 * Created by yuchengren on 2018/12/10.
 */
class SimpleResult<T>: Result<T>() {
    companion object {
        fun <T> fail(status: String?,tr:Throwable? = null): SimpleResult<T>{
            return SimpleResult<T>().apply {
                setSuccess(false)
                setStatus(status)
                setException(tr)
            }
        }
    }


}