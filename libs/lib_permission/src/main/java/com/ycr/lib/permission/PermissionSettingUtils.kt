package com.ycr.lib.permission

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings

/**
 * 跳转权限设置页面
 * Created by yuchengren on 2019/06/06.
 */

object PermissionSettingUtils {

    const val MF_HUAWEI = "huawei"
    const val MF_XIAOMI = "xiaomi"
    const val MF_OPPO = "oppo"
    const val MF_VIVO = "vivo"
    const val MF_MEIZU = "meizu"

    private const val PACKAGE_URL_SCHEME = "package:"

    // 启动应用的设置
    @JvmStatic fun startPermissionSettingsPage(context: Activity, code: Int) {
        val intent = buildPermissionSettingIntent(context)
        if (isSupportIntent(context, intent)) {
            try {
                context.startActivityForResult(intent, code)
            } catch (e: Exception) {
                context.startActivityForResult(getDefaultPermissionSettingIntent(context), code)
            }
        } else {
            context.startActivityForResult(getDefaultPermissionSettingIntent(context), code)
        }
    }

    private fun buildPermissionSettingIntent(context: Activity): Intent {
        val mf = Build.MANUFACTURER.toLowerCase()
        return when {
            mf.contains(MF_MEIZU) ->  getMeiZuIntent(context)
            mf.contains(MF_XIAOMI) -> getXiaoMiIntent(context)
            mf.contains(MF_HUAWEI) -> getHuaWeiIntent(context)
            mf.contains(MF_VIVO) -> getVivoIntent(context)
            mf.contains(MF_OPPO) -> getOppoIntent(context)
            else -> getDefaultPermissionSettingIntent(context)
        }
    }

    private fun getVivoIntent(context: Activity): Intent {
        val intent = Intent()
        intent.putExtra("packagename", context.packageName)
        intent.component = ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity")
        if (isSupportIntent(context, intent)) return intent

        intent.component = ComponentName("com.iqoo.secure", "com.iqoo.secure.safeguard.SoftPermissionDetailActivity")
        return intent
    }

    private fun getXiaoMiIntent(context: Activity): Intent {
        val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
        intent.putExtra("extra_pkgname", context.packageName)
        return intent
    }


    private fun getOppoIntent(context: Activity): Intent {
        val intent = Intent()
        intent.putExtra("packageName", context.packageName)
        intent.component = ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity")
        return intent
    }

    private fun getHuaWeiIntent(context: Activity): Intent {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getDefaultPermissionSettingIntent(context)
        }
        val intent = Intent()
        intent.component = ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity")
        return intent
    }

    private fun getMeiZuIntent(context: Activity): Intent {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return getDefaultPermissionSettingIntent(context)
        }
        val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
        intent.putExtra("packageName", context.packageName)
        intent.component = ComponentName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity")
        return intent
    }

    private fun getDefaultPermissionSettingIntent(context: Activity): Intent {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse(PACKAGE_URL_SCHEME + context.packageName)
        return intent
    }

    private fun isSupportIntent(context: Context, intent: Intent): Boolean {
        val packageManager = context.packageManager
        val activities = packageManager?.queryIntentActivities(intent, 0)
        return activities != null && activities.size > 0
    }
}
