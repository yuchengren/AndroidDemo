package com.ycr.kernel.json.parse

/**
 * Created by yuchengren on 2018/12/17.
 */
interface IJsonObject: IJsonElement {
    fun getBoolean(key: String,defaultValue: Boolean): Boolean
    fun getString(key: String,defaultValue: String?): String?

    fun get(key: String): IJsonElement?
    fun getAsJsonArray(key: String): IJsonArray?
    fun getAsJsonObject(key: String): IJsonObject?
}