package com.yuchengren.demo.hotfix

/**
 * created by yuchengren on 2019/5/25
 */
object HotFixManager {
    /**
     * 无补丁包
     */
    private const val PATCH_NONE = 0
    /**
     * 本地补丁包
     */
    private const val PATCH_LOCAL = -1

    @Volatile
    private var patchVersion: String = ""

    fun setPatchVersion(version: Int){
        if(version == PATCH_NONE || version == PATCH_LOCAL){
            patchVersion = ""
            return
        }
        patchVersion = version.toString()
    }

    fun getPatchVersion(): String{
        return patchVersion
    }

}