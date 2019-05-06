package com.ycr.lib.permission

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.permission_activity_permission_proxy.*

/**
 * Created by yuchengren on 2019/3/1.
 */
abstract class PermissionProxyActivity: AppCompatActivity() {
    companion object {
        const val CODE_REQUEST_PERMISSIONS = 1
    }

    lateinit var permissionModule: PermissionModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.permission_activity_permission_proxy)

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

    fun checkPermissionActions(permissionActions: Array<PermissionAction>) {
        permissionActions.forEach {
            it.isGranted = PermissionHelper.checkPermissions(this,it.permissions)
        }
        checkAllPermissionActionsGranted()
    }

    private fun checkAllPermissionActionsGranted(){
        if(isAllPermissionActionsGranted()){
            setResult(PermissionHelper.CODE_RESULT_GRANTED,intent)
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
        if(PermissionHelper.checkPermissions(this,permissionAction.permissions)){
            permissionAction.isGranted = true
            notifyAdapter()
        }else{
            PermissionHelper.requestPermissions(this,CODE_REQUEST_PERMISSIONS,permissionAction.permissions)
        }
    }

    private fun notifyAdapter(){
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        setResult(PermissionHelper.CODE_RESULT_DEFINED,intent)
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val allPermissionGranted = isAllPermissionGranted(grantResults)
        for (permissionAction in permissionModule.actionArray) {
            if(permissionAction.permissions == permissions){
                permissionAction.isGranted = allPermissionGranted
                if(!allPermissionGranted){
                    if(!PermissionHelper.shouldShowRequestPermissionRationale(this,permissions)){
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

    private fun isAllPermissionGranted(grantResults: IntArray): Boolean{
        for (grantResult in grantResults) {
            if(grantResult != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }

    abstract fun showGotoSetting(permissionAction: PermissionAction)
}