package com.ycr.kernel.union.http

import com.ycr.kernel.http.IApi
import com.ycr.kernel.http.IResult

/**
 * Created by yuchengren on 2019/2/28.
 */
open class UnionManager {

    fun <T> execute(api: IApi,params: Any?): IResult<T>{
        return HttpHelper.execute(api, params)
    }
}