package com.yuchengren.demo.app

import com.ycr.module.base.BaseApplication
import com.yuchengren.demo.init.HotFixInit

/**
 * created by yuchengren on 2019-06-17
 */
class DemoApplication: BaseApplication() {

    override fun doInit(isMainProcess: Boolean, processName: String?) {
        super.doInit(isMainProcess, processName)
        appInitSet.add(HotFixInit("HotFix"))
    }

}