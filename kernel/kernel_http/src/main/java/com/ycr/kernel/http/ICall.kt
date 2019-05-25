package com.ycr.kernel.http

/**
 * Created by yuchengren on 2018/12/13.
 */
interface ICall {
    fun execute(): IResponse?
    fun cancel()
}