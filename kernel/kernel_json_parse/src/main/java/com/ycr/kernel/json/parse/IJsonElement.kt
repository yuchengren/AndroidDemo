package com.ycr.kernel.json.parse

/**
 * Created by yuchengren on 2018/12/17.
 */
interface IJsonElement {
    fun getAsJsonArray(): IJsonArray?
    fun getAsJsonObject(): IJsonObject?

    fun isJsonArray(): Boolean
    fun isJsonObject(): Boolean
}