package com.ycr.kernel.union.http

import com.ycr.kernel.http.IApi
import com.ycr.kernel.http.IRequest
import com.ycr.kernel.http.IRequestBuilder

/**
 * Created by yuchengren on 2018/12/13.
 */
open abstract class UnionRequestBuilder<T: UnionRequest>(var api: IApi): IRequestBuilder {
    private var params: Any? = null

    abstract fun newInstance(): T


    override fun build(): IRequest {
        return newInstance().apply {
            setParams(params)
            setApi(api)
        }
    }

    override fun setParams(params: Any?):IRequestBuilder {
        this.params = params
        return this
    }


}