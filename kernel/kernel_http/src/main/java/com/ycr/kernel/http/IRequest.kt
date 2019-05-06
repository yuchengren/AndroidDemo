package com.ycr.kernel.http

/**
 * Created by yuchengren on 2018/12/13.
 */
interface IRequest {
    fun setParams(params: Any?)
    fun setApi(api: IApi)

    fun api(): IApi
    fun params():MutableMap<String, Any?>?

}