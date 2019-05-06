package com.ycr.kernel.union.http

import com.ycr.kernel.http.*
import java.lang.reflect.Type

/**
 * Created by yuchengren on 2018/12/13.
 */
open class UnionApi: IApi {

    private var url: String? = null

    var host: IHost? = null
    var headers: MutableMap<String, String>? = null

    var serverData: Any? = null
    var paramBuilder: IParamBuilder? = null

    lateinit var resultParser: IResultParser
    var type: Type? = null

    var requestMethod: String = RequestMethod.POST
    var contentType: ContentType = ContentType.APP_JSON
    var paramType: ParamType = ParamType.JSON

    override fun url(): String? {
        if(url != null){
            return url
        }
        host?.let {
            url = it.host() + (it.path()?:"")
        }
        return url
    }


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

    override fun serverData(): Any? {
        return serverData
    }

    override fun resultParser(): IResultParser {
        return resultParser
    }

    override fun paramBuilder(): IParamBuilder? {
        return paramBuilder
    }

    override fun requestMethod(): String {
        return requestMethod
    }

    override fun contentType(): ContentType {
        return contentType
    }

    override fun paramType(): ParamType {
        return paramType
    }

}