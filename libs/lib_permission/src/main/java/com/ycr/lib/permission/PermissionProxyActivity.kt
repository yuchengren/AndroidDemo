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
            setResult(PermissionHelper.CODE_RESULT_GRANTED,intent)
            finish()
            return
        }
        this.permissionModule = permissionModule
        checkPermissionActions(permissionModule.actionArray)
        showPermissionModule(permissionModule)
    }

    abstract fun showPermissionModule(permissionModule: PermissionModule)

    private fun checkPermissionActions(permissionActions: Array<out PermissionAction>) {
        var isHasPermissionChanged = false
        permissionActions.forEach {
            val checkPermissions = PermissionHelper.checkPermissions(this, * it.permissions)
            if(it.isGranted != checkPermissions){
                it.isGranted = checkPermissions
                isHasPermissionChanged = true
            }
        }
        if(isHasPermissionChanged){
            notifyAdapter()
        }
        checkAllPermissionActionsGranted()
    }

    private fun checkAllPermissionActionsGranted(){
        if(isAllPermissionActionsGranted()){
            setResult(PermissionHelper.CODE_RESULT_GRANTED,intent)
            finish()
        }else{
            setResult(PermissionHelper.CODE_RESULT_DEFINED,intent)
        }
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
        val isAllPermissionGranted = isAllPermissionGranted(grantResults)
        for (permissionAction in permissionModule.actionArray) {
            if(permissionAction.permissions.contentEquals(permissions)){
                if(permissionAction.isGranted != isAllPermissionGranted){
                    permissionAction.isGranted = isAllPermissionGranted
                    notifyAdapter()
                }
                if(!isAllPermissionGranted){
                    if(!PermissionHelper.shouldShowRequestPermissionRationale(this,*permissions)){
                        showGotoSetting(permissionAction)
                    }
                }
                break
            }
        }
        if(isAllPermissionGranted){
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