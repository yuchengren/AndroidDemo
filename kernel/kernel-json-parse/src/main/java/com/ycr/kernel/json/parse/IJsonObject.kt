package com.ycr.kernel.json.parse

/**
 * Created by yuchengren on 2018/12/17.
 */
interface IJsonObject {

    fun get(key: String): IJsonElement?
    fun getAsJsonArray(key: String): IJsonArray?
    fun getAsJsonObject(key: String): IJsonObject?
}