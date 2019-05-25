package com.ycr.kernel.union.task

/**
 * Created by yuchengren on 2018/12/10.
 */
class SimpleResult<T>: Result<T>() {
    companion object {
        fun <T> fail(status: String?,tr:Throwable?): SimpleResult<T> {
            return SimpleResult<T>().apply {
                setSuccess(false)
                setStatus(status)
                setException(tr)
            }
        }

        fun <T> fail(status: String?,msg: String? = null,code: String? = null): SimpleResult<T> {
            return SimpleResult<T>().apply {
                setSuccess(false)
                setStatus(status)
                setMsg(msg)
                setCode(code)
            }
        }

        fun <T> success(msg: String? = null,code: String? = null,data: T?): SimpleResult<T> {
            return SimpleResult<T>().apply {
                setSuccess(true)
                setMsg(msg)
                setCode(code)
                setData(data)
            }
        }
    }


}