package com.ycr.kernel.union.http

import com.ycr.kernel.http.IApi
import com.ycr.kernel.http.IParamBuilder
import com.ycr.kernel.http.IRequestBuilder
import com.ycr.kernel.http.IResultParser
import java.lang.reflect.Type

/**
 * Created by yuchengren on 2018/12/13.
 */
class UnionApi: IApi {

    override fun url(): String {
        return ""
    }

    var headers: MutableMap<String, String>? = null
    var type: Type? = null
    var paramData: Any? = null
    lateinit var resultParser: IResultParser

    override fun headers(): MutableMap<String, String>? {
        return headers
    }

    override fun resultType(): Type? {
        return type
    }

    override fun newRequestBuilder(): IRequestBuilder {
        return object: UnionRequestBuilder<UnionRequest>(this){
            override fun newInstance(): UnionRequest {
                return UnionRequest()
            }
        }
    }

    override fun paramData(): Any? {
        return paramData
    }

    override fun resultParser(): IResultParser {
        return resultParser
    }
}