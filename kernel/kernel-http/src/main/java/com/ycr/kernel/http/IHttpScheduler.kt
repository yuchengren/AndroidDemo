package com.ycr.kernel.http

/**
 * Created by yuchengren on 2018/12/13.
 */
interface IHttpScheduler{
    fun newCall(request: IRequest): ICall
    fun execute(call: ICall, groupName: String? = null, taskName: String? = null): IResponse
    fun <T> parse(api: IApi, response: IResponse): IResult<T>
}