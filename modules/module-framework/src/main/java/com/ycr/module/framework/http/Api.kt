package com.ycr.module.framework.http

import com.ycr.kernel.http.ParamType
import com.ycr.kernel.http.RequestMethod
import com.ycr.kernel.union.helper.ContextHelper
import com.ycr.kernel.union.http.UnionApi
import com.ycr.kernel.util.ICharsets
import com.ycr.module.framework.helper.SysHelper
import java.lang.reflect.Type


/**
 * Created by yuchengren on 2018/12/18.
 */
class Api: UnionApi() {
    private val normalHeaders = mutableMapOf<String,String>()
    init {
        normalHeaders["User-Agent"] = SysHelper.getUserAgent(ContextHelper.context)
        normalHeaders["Client-Agent"] = SysHelper.getClientInfo(ContextHelper.context)
        normalHeaders["Accept-Charset"] = ICharsets.UTF_8
    }

    var isNeedCheckData: Boolean = true //是否需要校验解析数据
    var isNeedToken: Boolean = true //是否需要token

    companion object {
        fun POST(serviceName: String,resultType: Type?): Api{
            return Api().apply {
                host = Hosts.api
                headers = normalHeaders
                serverData = serviceName
                paramBuilder = ApiParamBuilder
                resultParser = ApiResultParser
                type = resultType

                requestMethod = RequestMethod.POST
                paramType = ParamType.JSON
                isNeedCheckData = resultType != null
            }
        }
    }

}