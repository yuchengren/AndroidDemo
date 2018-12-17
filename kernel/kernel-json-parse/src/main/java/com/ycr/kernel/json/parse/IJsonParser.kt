package com.ycr.kernel.json.parse

import java.lang.reflect.Type

/**
 * Created by yuchengren on 2018/12/15.
 */
interface IJsonParser{
    fun toJson(any: Any?): String?

    fun <T> fromJson(json: String?,type: Type?): T?
    fun <T> fromJson(json: IJsonElement?,type: Type?): T?
    fun <T> fromJson(json: IJsonObject?,type: Type?): T?
    fun <T> fromJson(json: IJsonArray?,type: Type?): T?

    fun parseJsonToElement(json: String?): IJsonElement?
}