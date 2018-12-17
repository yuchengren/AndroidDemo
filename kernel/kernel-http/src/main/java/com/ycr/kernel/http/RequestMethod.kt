package com.ycr.kernel.http

import android.support.annotation.StringDef
import com.ycr.kernel.http.RequestMethod.Companion.GET
import com.ycr.kernel.http.RequestMethod.Companion.POST
import com.ycr.kernel.http.RequestMethod.Companion.PUT

/**
 * Created by yuchengren on 2018/12/13.
 */
@StringDef(GET, POST, PUT)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE)
annotation class RequestMethod{
    companion object {
        const val GET = "GET"
        const val POST = "POST"
        const val PUT = "PUT"
    }
}