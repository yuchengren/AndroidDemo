package com.ycr.kernel.http.okhttp

import com.ycr.kernel.http.IResponse
import okhttp3.Response
import java.io.InputStream

/**
 * Created by yuchengren on 2018/12/15.
 */
class OkHttpResponse(var response: Response): IResponse {

    override val totalLength: Long
        get() = response.body()?.contentLength()?:0

    override val inputStream: InputStream?
        get() = response.body()?.byteStream()

    override fun isSuccess(): Boolean {
        return response.isSuccessful
    }

    override fun body(): String? {
        return response.body().string()
    }

    override fun responseCode(): Int {
        return response.code()
    }

    override fun header(key: String, defaultValue: String?): String? {
        return response.header(key,defaultValue)
    }
}