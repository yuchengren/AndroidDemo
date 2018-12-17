package com.ycr.kernel.union.http

import com.ycr.kernel.http.IApi
import com.ycr.kernel.http.IRequest

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
        return null
    }



    override fun setParams(params: Any?){
        this.params = params
    }
}