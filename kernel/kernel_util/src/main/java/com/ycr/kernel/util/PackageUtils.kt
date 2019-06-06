package com.ycr.kernel.util

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo

/**
 * Created by yuchengren on 2018/12/18.
 */

fun Context.getPackageInfo(): PackageInfo? {
    return packageManager?.getPackageInfo(packageName, 0)
}

fun Context.getAppVersionCode(): Int {
    return getPackageInfo()?.versionCode ?: return 0
}

fun Context.getAppVersionName(): String {
    return getPackageInfo()?.versionName ?: Strings.EMPTY
}

fun Context.getAppInfo(): ApplicationInfo?{
    return packageManager?.getApplicationInfo(packageName,0)
}

fun Context.getAppName(): String {
    return packageManager?.getApplicationLabel(getAppInfo())?.toString()?:""
}
