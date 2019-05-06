package com.ycr.kernel.http

/**
 * 网络请求的参数类型
 * Created by yuchengren on 2018/12/15.
 */
enum class ParamType {
    NORMAL, //普通key value类型
    FILE, //文件类型
    JSON //json字符串类型
}