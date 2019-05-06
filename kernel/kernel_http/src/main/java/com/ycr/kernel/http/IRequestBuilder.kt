package com.ycr.kernel.http

/**
 * Created by yuchengren on 2018/12/13.
 */
interface IRequestBuilder {
    fun setParams(params: Any?): IRequestBuilder
    fun build(): IRequest
}