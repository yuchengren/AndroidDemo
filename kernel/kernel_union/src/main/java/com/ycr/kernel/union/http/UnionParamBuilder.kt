package com.ycr.kernel.union.http

import com.ycr.kernel.http.IApi
import com.ycr.kernel.http.IParamBuilder

/**
 * Created by yuchengren on 2018/12/15.
 */
object UnionParamBuilder: IParamBuilder {

    override fun buildParam(api: IApi, serverData: Any?, params: Any?): MutableMap<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        if(serverData is MutableMap<*, *>){
            map.putAll(serverData as Map<out String, Any?>)
        }
        if(params is MutableMap<*, *>){
            map.putAll(params as Map<out String, Any?>)
        }
        return map
    }
}