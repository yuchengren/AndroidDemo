package com.ycr.module.framework.http

import com.ycr.kernel.http.IApi
import com.ycr.kernel.http.IResult
import com.ycr.kernel.union.helper.JsonHelper
import com.ycr.module.framework.FrameworkModuleLog
import com.ycr.module.framework.task.ResultStatus
import com.ycr.kernel.union.task.SimpleResult
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by yuchengren on 2018/12/18.
 */
object ApiResultParser : AbstractResultParser(){

    override fun <T> doParse(type: Type?, body: String, api: IApi): IResult<T> {
        val jsonElement = JsonHelper.parseJsonToElement(body) ?: return SimpleResult.fail(ResultStatus.DATA_ERROR)
        val jsonObject = jsonElement.getAsJsonObject() ?: return SimpleResult.fail(ResultStatus.DATA_ERROR)
        val isSuccess = jsonObject.getBoolean("success", false)
        val code = jsonObject.getString("code", null)
        val msg = jsonObject.getString("message", null)
        if (!isSuccess){
            return SimpleResult.fail(ResultStatus.SERVER_ERROR, code, msg)
        }
        var resultData: T? = null
        jsonObject.getAsJsonObject("data")?.let {
            val resultObject = it.get("resultObject")
            when(resultObject){
                null ->{
                    var clazz: Class<*>? = null
                    if(type is Class<*>){
                        clazz = type
                    }else if(type is ParameterizedType){
                        val rawType:Type? = type.rawType
                        if(rawType is Class<*>){
                            clazz = rawType
                        }
                    }

                    if(clazz == null){
                        when(type){
                            null -> FrameworkModuleLog.i("ApiResultParser doParse, data is not null but type is null")
                            else -> resultData = JsonHelper.fromJson<T>(it,type)
                        }
                    }else{
                        if(IPage.javaClass.isAssignableFrom(clazz)){
                            resultData = JsonHelper.fromJson<T>(it,type)
                        }else if(Collection::class.java.isAssignableFrom(clazz) || clazz.isArray){
                            it.getAsJsonArray("resultList")?.run {resultData = JsonHelper.fromJson(this,clazz)}
                        }
                    }
                }
                else ->{
                    when(type){
                        null -> FrameworkModuleLog.i("ApiResultParser doParse, resultObject is not null but type is null")
                        else -> resultData = JsonHelper.fromJson<T>(resultObject,type)
                    }
                }
            }
        }

        if(resultData == null && api is Api && api.isNeedCheckData){
            return SimpleResult.fail(ResultStatus.DATA_MISS)
        }
        return SimpleResult.success(resultData, code, msg)
    }
}