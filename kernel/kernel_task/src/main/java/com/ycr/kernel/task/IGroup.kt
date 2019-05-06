package com.ycr.kernel.task

/**
 * Created by yuchengren on 2018/9/14.
 */

interface IGroup {
    companion object {
        const val GROUP_NAME_DEFAULT: String = "default-group"
        val defaultGroup = object : IGroup{
            override fun groupName(): String? {
                return GROUP_NAME_DEFAULT
            }
        }
    }
    fun groupName(): String?
}