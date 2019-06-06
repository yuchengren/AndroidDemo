package com.ycr.module.base.permission

import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.ycr.lib.permission.PermissionAction
import com.ycr.lib.permission.PermissionActionAdapter
import com.ycr.lib.permission.PermissionModule
import com.ycr.lib.permission.PermissionProxyActivity
import com.ycr.lib.theme.MessageDialogButtonStyle
import com.ycr.lib.ui.dialog.MessageDialogFragment
import com.ycr.module.base.R
import kotlinx.android.synthetic.main.permission_activity_permission_proxy.*

/**
 * Created by yuchengren on 2019/3/18.
 */
class PermissionModuleActivity : PermissionProxyActivity(),MessageDialogFragment.OnButtonClickListener {

    override fun getRootLayoutResId(): Int {
        return R.layout.permission_activity_permission_proxy
    }

    override fun showPermissionModule(permissionModule: PermissionModule) {
        ivClose.setOnClickListener { onBackPressed() }
        tvTitle.text = permissionModule.title
        tvContent.text = permissionModule.content

        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = PermissionActionAdapter(permissionModule.actionArray){ position, item ->
                checkPermissions(item)
            }
        }
    }

    override fun notifyAdapter() {
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun showGotoSetting(action: PermissionAction) {
        val contentText = if(TextUtils.isEmpty(action.gotoSettingDesc) && action.permissions.isNotEmpty())
            PermissionActionDescHelper.getGotoSettingDesc(this,action.permissions[0]) else action.gotoSettingDesc
        MessageDialogFragment.builder()
                .contentText(contentText)
                .buttonTexts("取消","去设置")
                .buttonStyles(MessageDialogButtonStyle.WEAK,MessageDialogButtonStyle.STRONG)
                .build().show(supportFragmentManager,"gotoSetting")
    }

    override fun onButtonClick(dialog: MessageDialogFragment, text: String, textResId: Int, position: Int): Boolean {
        if(position == 1){
            gotoSetting()
        }
        return true
    }

}