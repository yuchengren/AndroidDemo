package com.ycr.module.base.permission

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.ycr.lib.permission.*
import kotlinx.android.synthetic.main.permission_activity_permission_proxy.*

/**
 * Created by yuchengren on 2019/3/18.
 */
class PermissionModuleActivity : PermissionProxyActivity() {

    override fun showPermissionModule(permissionModule: PermissionModule) {
        ivClose.setOnClickListener { onBackPressed() }
        tvTitle.text = permissionModule.title
        tvContent.text = permissionModule.content

        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = PermissionActionApdater(permissionModule.actionArray){
                position, item -> checkPermissions(item)
            }
        }
    }

    override fun showGotoSetting(permissionAction: PermissionAction) {

    }

}