package com.ycr.kernel.union.helper

import android.content.Context

import com.ycr.kernel.json.parse.IJsonElement
import com.ycr.kernel.json.parse.IJsonParser

import java.lang.reflect.Type

/**
 * created by yuchengren on 2019/4/22
 */
object JsonHelper {

    private val jsonParser = UnionContainer.jsonParser

    fun toJson(any: Any?): String? {
        return jsonParser.toJson(any)
    }

    fun <T> fromJson(json: String?, type: Type?): T? {
        return jsonParser.fromJson<T>(json, type)
    }

    fun <T> fromJson(json: IJsonElement?, type: Type?): T? {
        return jsonParser.fromJson<T>(json, type)
    }

    fun parseJsonToElement(json: String?): IJsonElement? {
        return jsonParser.parseJsonToElement(json)
    }
}
