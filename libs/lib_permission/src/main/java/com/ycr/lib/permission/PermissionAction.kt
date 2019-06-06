package com.ycr.lib.permission

import java.io.Serializable
import java.util.*

/**
 * Created by yuchengren on 2019/3/14.
 */
class PermissionAction(vararg val permissions: String,
                            var permissionDesc: String? = "",
                            var gotoSettingDesc: String? = "",
                            var isGranted: Boolean = false): Serializable