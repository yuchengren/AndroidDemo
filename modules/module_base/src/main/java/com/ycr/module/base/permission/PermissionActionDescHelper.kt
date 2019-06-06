package com.ycr.module.base.permission

import android.Manifest
import android.content.Context

/**
 * created by yuchengren on 2019-06-06
 */
object PermissionActionDescHelper {

    @JvmStatic fun getActionDesc(permission: String): String{
        return "启用${getPermissionName(permission)}权限"
    }

    @JvmStatic fun getPermissionName(permission: String): String{
        return when(permission){
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> "本地文件访问"

            Manifest.permission.CAMERA -> "相机访问"

            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_PHONE_STATE -> "电话"

            Manifest.permission.RECORD_AUDIO -> "录制音频"

            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION -> "设备定位"


            else -> ""
        }
    }

    @JvmStatic fun getGotoSettingDesc(context: Context, permission: String): String{
        val packageManager = context.packageManager
        val appInfo = packageManager?.getApplicationInfo(context.packageName,0)
        val appName = packageManager?.getApplicationLabel(appInfo)?.toString()?:""
        val permissionName = getPermissionName(permission)

        return "${appName}需要启用${permissionName}权限"
    }
}