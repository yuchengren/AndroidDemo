package com.ycr.kernel.http

import java.io.InputStream

/**
 * Created by yuchengren on 2018/12/13.
 */
interface IResponse{
    companion object {
        const val CODE_SUCCESS = 200
    }

    val inputStream: InputStream?

    val totalLength: Long

    fun isSuccess(): Boolean

    fun body(): String?

    fun responseCode(): Int

    fun header(key: String, defaultValue: String?): String?



}