package com.ycr.kernel.util

/**
 * Created by yuchengren on 2018/12/13.
 */
object ThreadLocalHelper {
    private val threadLocal = ThreadLocal<MutableMap<String,Any?>?>()

    fun <T> getThreadLocalInfo(key: String): T?{
        val get = threadLocal.get()?:return null
        return get[key] as? T?
    }

    fun <T> put(key:String,value: T){
        var get = threadLocal.get()
        if(get == null){
            get = mutableMapOf()
            threadLocal.set(get)
        }
        get[key] = value
    }
}