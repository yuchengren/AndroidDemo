package com.ycr.kernel.http

import java.io.File

/**
 * Created by yuchengren on 2018/12/13.
 */
interface IHttpScheduler{
    fun newCall(request: IRequest): ICall
    fun execute(call: ICall, groupName: String? = null, taskName: String? = null): IResponse?
    fun <T> parse(api: IApi, response: IResponse?): IResult<T>
    fun cancelGroup(groupName: String?)
    fun download(call: ICall, saveFile: File, downProgress: IDownloadProgress?)
}