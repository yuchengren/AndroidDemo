package com.ycr.lib.permission

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * Created by yuchengren on 2019/3/1.
 */
abstract class PermissionProxyActivity: AppCompatActivity() {
    companion object {
        const val REQUEST_CODE_PERMISSIONS = 1001
        const val REQUEST_CODE_SYSTEM_SETTING = 1002
    }

    lateinit var permissionModule: PermissionModule

    abstract fun getRootLayoutResId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getRootLayoutResId())

        val permissionModule = intent?.getSerializableExtra(IPermissionConstants.EXTRA_PERMISSION_MODULE) as? PermissionModule
        if(permissionModule?.actionArray?.isEmpty() != false){
            Toast.makeText(this,getString(R.string.no_permission_need_request), Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        this.permissionModule = permissionModule
        checkPermissionActions(permissionModule.actionArray)
        showPermissionModule(permissionModule)
    }

    abstract fun showPermissionModule(permissionModule: PermissionModule)

    private fun checkPermissionActions(permissionActions: Array<out PermissionAction>) {
        permissionActions.forEach {
            it.isGranted = PermissionHelper.checkPermissions(this, * it.permissions)
        }
        checkAllPermissionActionsGranted()
    }

    private fun checkAllPermissionActionsGranted(){
        setResult(if(isAllPermissionActionsGranted())PermissionHelper.CODE_RESULT_GRANTED else
            PermissionHelper.CODE_RESULT_DEFINED,intent)

    }

    private fun isAllPermissionActionsGranted(): Boolean{
        for (permissionAction in permissionModule.actionArray) {
            if(!permissionAction.isGranted){
                return false
            }
        }
        return true
    }

    fun checkPermissions(permissionAction: PermissionAction) {
        if(PermissionHelper.checkPermissions(this,* permissionAction.permissions)){
            permissionAction.isGranted = true
            notifyAdapter()
        }else{
            PermissionHelper.requestPermissions(this,REQUEST_CODE_PERMISSIONS,* permissionAction.permissions)
        }
    }

    abstract fun notifyAdapter()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val allPermissionGranted = isAllPermissionGranted(grantResults)
        for (permissionAction in permissionModule.actionArray) {
            if(permissionAction.permissions.contentEquals(permissions)){
                permissionAction.isGranted = allPermissionGranted
                if(!allPermissionGranted){
                    if(!PermissionHelper.shouldShowRequestPermissionRationale(this,*permissions)){
                        showGotoSetting(permissionAction)
                    }
                }
                break
            }
        }
        if(allPermissionGranted){
            checkAllPermissionActionsGranted()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CODE_SYSTEM_SETTING -> checkPermissionActions(permissionModule.actionArray)
        }
    }

    private fun isAllPermissionGranted(grantResults: IntArray): Boolean{
        for (grantResult in grantResults) {
            if(grantResult != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }

    fun gotoSetting(){
        PermissionSettingUtils.startPermissionSettingsPage(this, REQUEST_CODE_SYSTEM_SETTING)
    }

    abstract fun showGotoSetting(permissionAction: PermissionAction)
}