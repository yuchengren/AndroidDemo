package com.ycr.kernel.union

import android.app.Application
import android.support.annotation.CallSuper
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.ycr.kernel.http.okhttp.OkHttpScheduler
import com.ycr.kernel.image.ImageDisplayType
import com.ycr.kernel.image.glide.GlideImageLoader
import com.ycr.kernel.image.glide.ImageDisplayOption
import com.ycr.kernel.json.parse.gson.GsonJsonParser
import com.ycr.kernel.union.helper.ContextHelper
import com.ycr.kernel.union.helper.UnionContainer
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
        UnionContainer.jsonParser = GsonJsonParser.doInit(Gson(), JsonParser())
        UnionContainer.httpScheduler = OkHttpScheduler.doInit(UnionContainer.jsonParser, OkHttpClient())

        val imageDisplayOption = ImageDisplayOption.build().
                cacheInMemory(true).
                cacheOnDisk(true).
                imageDisplayType(ImageDisplayType.CENTER_CROP)
        UnionContainer.imageLoader = GlideImageLoader(this, imageDisplayOption, null)
    }
}