package com.ycr.kernel.http

/**
 * Created by yuchengren on 2018/12/15.
 */
interface IParamBuilder {
    fun buildParam(vararg args: Any?): MutableMap<String,Any?>
}