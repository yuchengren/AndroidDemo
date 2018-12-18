package com.ycr.module.framework.http

import com.ycr.kernel.union.http.IHost

/**
 * Created by yuchengren on 2018/12/17.
 */
object Hosts {

    val api = object : IHost{

        override fun host(): String {
            return EnvHelper.nowEnv.apiHost
        }

        override fun path(): String? {
            return "/api.do"
        }
    }

    val bigData = object : IHost{

        override fun host(): String {
            return EnvHelper.nowEnv.bigDataHost
        }

        override fun path(): String? {
            return "app/api/request.do"
        }
    }
}