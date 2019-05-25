package com.ycr.module.framework.http

import com.ycr.kernel.http.IApi
import com.ycr.kernel.http.IResponse
import com.ycr.kernel.http.IResult
import com.ycr.kernel.http.IResultParser
import com.ycr.module.framework.task.ResultStatus
import com.ycr.kernel.union.task.SimpleResult
import java.lang.reflect.Type

/**
 * Created by yuchengren on 2018/12/18.
 */
abstract class AbstractResultParser: IResultParser {

    override fun <T> parse(type: Type?, response: IResponse?, api: IApi): IResult<T> {
        if(response?.header("loggedInOtherClient", null) == "1"){
            return SimpleResult.fail(ResultStatus.LOGIN_AT_OTHER_CLIENT,"你的账号已经在其他终端登录，请检查后重新登录")
        }
        if(response?.header("sessionTimeout", null) == "1"){
            return SimpleResult.fail(ResultStatus.LOGIN_SESSION_INVALID,"登录信息过期，请重新登录")
        }
        val body = response?.body()
        return if(body == null){
            SimpleResult.fail(ResultStatus.DATA_EMPTY)
        }else{
            doParse(type,body,api)
        }
    }

    abstract fun <T> doParse(type: Type?,body: String,api: IApi): IResult<T>
}