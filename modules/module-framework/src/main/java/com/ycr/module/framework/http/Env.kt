package com.ycr.module.framework.http

/**
 * Created by yuchengren on 2018/12/17.
 */
data class Env(val name: String,//环境名称
               val system: String,//后台系统
               val server: String,//后台系统的服务器
               val apiHost: String, //普通后台接口的host
               val bigDataHost: String, //大数据上报host
               val logHost: String, //日志host
               val fileHost: String) {//文件上传host
    companion object {
        val online = Env("online","demo","demo-app",
                "http://localhost:8080",
                "",
                "",
                "")
    }
}