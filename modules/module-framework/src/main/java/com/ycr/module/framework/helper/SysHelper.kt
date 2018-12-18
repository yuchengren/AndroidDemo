package com.ycr.module.framework.helper

import android.content.Context
import com.ycr.kernel.util.SystemUtils
import com.ycr.kernel.util.getAppVersionName
import com.ycr.kernel.util.getPhonePixels

/**
 * Created by yuchengren on 2018/12/18.
 */
object SysHelper {
    private var userAgent: String = ""
    private var clientInfo: String = ""


    fun getUserAgent(context: Context): String{
        if(userAgent.isNullOrEmpty()){
            userAgent = "Demo/${context.getAppVersionName()}(${SystemUtils.getSystemModel()} Android ${SystemUtils.getSystemVersion()})"
        }
        return userAgent
    }

    fun getClientInfo(context: Context): String{
        if(clientInfo.isNullOrEmpty()){
            clientInfo = "Demo_Android_JAVA_1_0_0/${context.getPhonePixels()}/${SystemUtils.getSystemManufacturer()}/${SystemUtils.getSystemModel()}"
        }
        return clientInfo
    }
}