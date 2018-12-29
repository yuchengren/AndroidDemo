package com.ycr.kernel.json.parse

/**
 * Created by yuchengren on 2018/12/17.
 */
interface IJsonArray : IJsonElement{
    fun get(index: Int): IJsonElement?
}