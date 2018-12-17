package com.ycr.kernel.union

import android.app.Application
import android.support.annotation.CallSuper
import com.ycr.kernel.union.helper.ContextHelper
import com.ycr.kernel.union.http.HttpHelper
import com.ycr.kernel.util.getProcessName
import com.ycr.kernel.util.isMainProcess

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
    }


}