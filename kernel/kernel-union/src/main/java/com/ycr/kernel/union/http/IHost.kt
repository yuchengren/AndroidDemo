package com.ycr.kernel.union.http

/**
 * Created by yuchengren on 2018/12/17.
 */
interface IHost {
    fun host(): String
    fun path(): String?
}