package com.ycr.kernel.http

/**
 * Created by yuchengren on 2018/12/3.
 */
interface IResult<T> {
    fun code(): String?
    fun msg(): String?
    fun data():T?
    fun isSuccess(): Boolean
    fun status(): String?
    fun exception(): Throwable?
}