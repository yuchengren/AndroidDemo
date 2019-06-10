package com.ycr.lib.permission

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

/**
 * Created by yuchengren on 2019/3/1.
 */
object PermissionHelper {

    const val CODE_RESULT_GRANTED = 13
    const val CODE_RESULT_DEFINED = 14

    @JvmStatic fun checkPermissions(context: Context, vararg permissions: String): Boolean{
        for (permission in permissions) {
            if(!checkPermission(context,permission)){
                return false
            }
        }
        return true
    }

    @JvmStatic fun checkPermission(context: Context, permission: String): Boolean{
        return ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED
    }

    @JvmStatic fun requestPermissions(activity: Activity,requestCode: Int,vararg permissions: String){
        ActivityCompat.requestPermissions(activity,permissions,requestCode)
    }

    @JvmStatic fun shouldShowRequestPermissionRationale(activity: Activity,vararg permissions: String): Boolean{
        var isShouldShow = true
        permissions.forEach {
            isShouldShow = isShouldShow && ActivityCompat.shouldShowRequestPermissionRationale(activity,it)
        }
        return isShouldShow
    }


    @JvmStatic fun startForResult(activity: Activity?,permissionActivity: Class<*>,
                                  requestCode: Int,permissionModule: PermissionModule?){
        if(activity == null || permissionModule == null){
            return
        }
        val intent = Intent(activity,permissionActivity).apply {
            putExtra(IPermissionConstants.EXTRA_PERMISSION_MODULE,permissionModule)
        }
        activity.startActivityForResult(intent,requestCode)
    }

    @JvmStatic fun startForResult(fragment: Fragment?,permissionActivity: Class<*>,
                                  requestCode: Int,permissionModule: PermissionModule?){
        if(fragment == null || permissionModule == null){
            return
        }
        val intent = Intent(fragment.context,permissionActivity).apply {
            putExtra(IPermissionConstants.EXTRA_PERMISSION_MODULE,permissionModule)
        }
        fragment.startActivityForResult(intent,requestCode)
    }

    @JvmStatic fun handlePermissionResult(resultCode: Int, result: IPermissionResult){
        when(resultCode){
            CODE_RESULT_GRANTED -> result.onGranted()
            else -> result.onDefined()
        }
    }
}