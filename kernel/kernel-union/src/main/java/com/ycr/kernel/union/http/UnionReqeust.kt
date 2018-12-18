package com.ycr.kernel.union.http

import com.ycr.kernel.http.IApi
import com.ycr.kernel.http.IRequest
import com.ycr.kernel.union.UnionLog

/**
 * Created by yuchengren on 2018/12/13.
 */
class UnionRequest: IRequest {

    private lateinit var api: IApi
    private var url: String? = null

    private var params: Any? = null

    override fun setApi(api: IApi) {
        this.api = api
    }

    override fun api(): IApi {
        return api
    }

    override fun params(): MutableMap<String, Any?>? {
        val paramBuilder = api.paramBuilder()
        return when {
            paramBuilder != null ->  paramBuilder.buildParam(api, params, api.serverData())
            params is MutableMap<*, *> ->  params as  MutableMap<String, Any?>
            else -> {
                UnionLog.e("UnionRequest paramBuilder is null and params is not Map")
                null
            }
        }
    }

    override fun setParams(params: Any?){
        this.params = params
    }
}