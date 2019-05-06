package com.ycr.module.framework.http

import com.ycr.kernel.http.IApi
import com.ycr.kernel.http.IParamBuilder
import com.ycr.kernel.union.helper.JsonHelper

/**
 * Created by yuchengren on 2018/12/18.
 */
object ApiParamBuilder: IParamBuilder {

    override fun buildParam(api: IApi, serverData: Any?, params: Any?): MutableMap<String, Any?> {
        val requestParam = mutableMapOf<String,Any?>()
        val bizParams = JsonHelper.toJson(params)
        requestParam["param"] = bizParams // 访问接口的业务参数
        requestParam["service_name"] = serverData //用来指定访问的后台服务对应的接口名

        requestParam["timestamp"] = System.currentTimeMillis() //时间戳
        requestParam["sign"] = "" //签名 bizParams、serverData、timestamp加密生成
        requestParam["secret_state"] = "" //加密状态 是否加密
        requestParam["client_id"] = "" //客户端id 用来区分前端、Android、IOS

        if(api is Api){
            if(api.isNeedToken){
                requestParam["token"] = "" //User中的token
            }
        }
        return requestParam
    }
}