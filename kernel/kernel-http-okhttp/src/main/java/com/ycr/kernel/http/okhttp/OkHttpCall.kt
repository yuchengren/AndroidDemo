package com.ycr.kernel.http.okhttp

import com.ycr.kernel.http.ApiCall
import com.ycr.kernel.http.IResponse
import okhttp3.Call

/**
 * Created by yuchengren on 2018/12/15.
 */
class OkHttpCall(var call:Call): ApiCall() {

    override fun execute(): IResponse {
        return OkHttpResponse(call.execute())
    }

    override fun cancel() {
        call.cancel()
    }
}