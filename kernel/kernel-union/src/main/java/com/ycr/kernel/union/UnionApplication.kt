package com.ycr.kernel.union

import android.app.Application
import android.support.annotation.CallSuper
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.ycr.kernel.http.okhttp.OkHttpScheduler
import com.ycr.kernel.json.parse.gson.GsonJsonParser
import com.ycr.kernel.union.helper.ContextHelper
import com.ycr.kernel.union.helper.JsonHelper
import com.ycr.kernel.union.http.HttpHelper
import com.ycr.kernel.util.getProcessName
import com.ycr.kernel.util.isMainProcess
import okhttp3.OkHttpClient

/**
 * Created by yuchengren on 2018/12/7.
 */
open class UnionApplication : Application() {

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        doInit(isMainProcess(), getProcessName())
    }

    @CallSuper
    open fun doInit(isMainProcess: Boolean, processName: String?){
        ContextHelper.doInit(this)
        GsonJsonParser.doInit(Gson(), JsonParser())
        JsonHelper.doInt(GsonJsonParser)

        OkHttpScheduler.doInit(GsonJsonParser, OkHttpClient())
        HttpHelper.doInit(this,OkHttpScheduler)
    }


}