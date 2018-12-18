package com.ycr.kernel.http

import java.lang.reflect.Type

/**
 * Created by yuchengren on 2018/12/13.
 */
interface IApi{

    fun url(): String?
    fun requestMethod(): @RequestMethod String
    fun headers(): MutableMap<String,String>?
    fun contentType(): ContentType
    fun paramType(): ParamType
    fun serverData(): Any?
    fun paramBuilder(): IParamBuilder?

    fun resultParser(): IResultParser
    fun resultType(): Type?

    fun newRequestBuilder(): IRequestBuilder
}