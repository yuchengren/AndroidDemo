package com.ycr.kernel.json.parse.gson

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.ycr.kernel.json.parse.IJsonArray
import com.ycr.kernel.json.parse.IJsonElement
import com.ycr.kernel.json.parse.IJsonObject

/**
 * Created by yuchengren on 2018/12/17.
 */
class GsonJsonObject(val jsonObject: JsonObject?): GsonJsonElement(jsonObject),IJsonObject {
    override fun get(key: String): IJsonElement? {
        return GsonJsonElement(jsonObject?.get(key))
    }

    override fun getAsJsonArray(key: String): IJsonArray? {
        if(jsonObject != null){
            val jsonElement: JsonElement? = jsonObject.get(key)
            if(jsonElement != null && jsonElement is JsonArray){
                return GsonJsonArray(jsonElement)
            }
        }
        return null
    }

    override fun getAsJsonObject(key: String): IJsonObject? {
        if(jsonObject != null){
            val jsonElement: JsonElement? = jsonObject.get(key)
            if(jsonElement != null && jsonElement is JsonObject){
                return GsonJsonObject(jsonElement)
            }
        }
        return null

    }
}