package com.ycr.kernel.http

import java.lang.reflect.Type

/**
 * Created by yuchengren on 2018/12/13.
 */
interface IResultParser{
    fun <T> parse(type: Type?, response: IResponse?, api: IApi): IResult<T>
}