package com.ycr.kernel.union.helper

import com.ycr.kernel.json.parse.IJsonArray
import com.ycr.kernel.json.parse.IJsonElement
import com.ycr.kernel.json.parse.IJsonObject
import com.ycr.kernel.json.parse.IJsonParser
import java.lang.reflect.Type

/**
 * Created by yuchengren on 2018/12/18.
 */
object JsonHelper: IJsonParser{
    private lateinit var jsonParser: IJsonParser

    fun doInt(jsonParser: IJsonParser){
        this.jsonParser = jsonParser
    }

    override fun toJson(any: Any?): String? {
        return jsonParser.toJson(any)
    }

    override fun <T> fromJson(json: String?, type: Type?): T? {
        return jsonParser.fromJson(json,type)
    }

    override fun <T> fromJson(json: IJsonElement?, type: Type?): T? {
        return jsonParser.fromJson(json,type)
    }

    override fun parseJsonToElement(json: String?): IJsonElement? {
        return jsonParser.parseJsonToElement(json)
    }
}