package com.ycr.kernel.http

/**
 * Created by yuchengren on 2018/12/15.
 */
interface IParamBuilder {
    fun buildParam(api: IApi, serverData: Any?, params: Any?): MutableMap<String,Any?>
}