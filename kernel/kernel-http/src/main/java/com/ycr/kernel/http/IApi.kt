package com.ycr.kernel.http

import java.lang.reflect.Type

/**
 * Created by yuchengren on 2018/12/13.
 */
interface IApi{
    fun url(): String
    fun requestMethod(): @RequestMethod String = RequestMethod.POST
    fun headers(): MutableMap<String,String>?
    fun contentType(): ContentType = ContentType.APP_JSON
    fun paramType(): ParamType = ParamType.JSON
    fun paramData(): Any?

    fun resultParser(): IResultParser
    fun resultType(): Type?

    fun newRequestBuilder(): IRequestBuilder
}