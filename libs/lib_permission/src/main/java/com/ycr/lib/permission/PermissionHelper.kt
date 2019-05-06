package com.ycr.lib.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

/**
 * Created by yuchengren on 2019/3/1.
 */
object PermissionHelper {

    const val CODE_RESULT_GRANTED = 101
    const val CODE_RESULT_DEFINED = 102

    fun checkPermissions(context: Context, permissionArray: Array<String>): Boolean{
        for (permission in permissionArray) {
            if(!checkPermission(context,permission)){
                return false
            }
        }
        return true
    }

    fun checkPermission(context: Context, permission: String): Boolean{
        return ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissions(activity: Activity,requestCode: Int,permissions: Array<String>){
        ActivityCompat.requestPermissions(activity,permissions,requestCode)
    }

    fun shouldShowRequestPermissionRationale(activity: Activity,permissions: Array<String>): Boolean{
        var isShouldShow = true
        permissions.forEach {
            isShouldShow = isShouldShow && ActivityCompat.shouldShowRequestPermissionRationale(activity,it)
        }
        return isShouldShow
    }
}