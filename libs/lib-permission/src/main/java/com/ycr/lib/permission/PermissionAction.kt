package com.ycr.lib.permission

import java.io.Serializable
import java.util.*

/**
 * Created by yuchengren on 2019/3/14.
 */
data class PermissionAction(val permissions: Array<String>,var permissionDesc: String? = "启用权限",var isGranted: Boolean = false): Serializable