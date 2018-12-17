package com.ycr.kernel.json.parse.gson

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.ycr.kernel.json.parse.IJsonArray
import com.ycr.kernel.json.parse.IJsonElement
import com.ycr.kernel.json.parse.IJsonObject

/**
 * Created by yuchengren on 2018/12/17.
 */
open class GsonJsonElement(var jsonElement: JsonElement?) : IJsonElement {

    override fun getAsJsonArray(): IJsonArray? {
        try {
            if(jsonElement != null && jsonElement is JsonArray){
                return GsonJsonArray(jsonElement?.asJsonArray)
            }
        }catch (e: Exception){
            printExceptionLog(e,"getAsJsonArray")
        }
        return null
    }

    override fun getAsJsonObject(): IJsonObject? {
        try {
            if(jsonElement != null && jsonElement is JsonArray){
                return GsonJsonObject(jsonElement?.asJsonObject)
            }
        }catch (e: Exception){
            printExceptionLog(e,"getAsJsonObject")
        }
        return null
    }

    override fun isJsonArray(): Boolean {
        return jsonElement?.isJsonArray ?: return false
    }

    override fun isJsonObject(): Boolean {
        return jsonElement?.isJsonObject ?: return false
    }

    private fun printExceptionLog(e: Exception, period: String) {
        GsonJsonLog.e(e, " ${javaClass.simpleName} occur exception at $period ")
    }
}