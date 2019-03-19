package com.ycr.lib.permission

import java.io.Serializable

/**
 * Created by yuchengren on 2019/3/14.
 */
data class PermissionModule(var title: String?, var content: String?, var actionArray: Array<PermissionAction>): Serializable