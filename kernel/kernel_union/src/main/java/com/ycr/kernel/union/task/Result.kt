package com.ycr.kernel.union.task

import com.ycr.kernel.http.IResult

/**
 * Created by yuchengren on 2018/12/7.
 */
open class Result<T>: IResult<T>{

    private var code: String? = ""
    private var msg: String? = ""
    private var data: T? = null
    private var isSuccess: Boolean = false
    private var status: String? = null
    private var exception: Throwable? = null

    override fun code(): String? {
        return code
    }

    override fun msg(): String? {
        return msg
    }

    override fun data(): T? {
        return data
    }

    override fun isSuccess(): Boolean {
        return isSuccess
    }

    override fun status(): String? {
        return status
    }

    override fun exception(): Throwable? {
        return exception
    }

    fun setCode(code: String?){
        this.code = code
    }

    fun setMsg(msg: String?){
        this.msg = msg
    }
    fun setData(data: T?){
        this.data = data
    }
    fun setSuccess(isSuccess: Boolean){
        this.isSuccess = isSuccess
    }
    fun setStatus(status: String?){
        this.status = status
    }

    fun setException(exception: Throwable?){
        this.exception = exception
    }



}