package com.ycr.kernel.json.parse.gson

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.ycr.kernel.json.parse.IJsonArray
import com.ycr.kernel.json.parse.IJsonElement

/**
 * Created by yuchengren on 2018/12/17.
 */
class GsonJsonArray(val jsonArray: JsonArray?): GsonJsonElement(jsonArray),IJsonArray {

    override fun get(index: Int): IJsonElement? {
        if(jsonArray != null){
            val jsonElement: JsonElement?= jsonArray.get(index)
            if(jsonElement != null){
                return GsonJsonElement(jsonElement)
            }
        }
        return null
    }
}