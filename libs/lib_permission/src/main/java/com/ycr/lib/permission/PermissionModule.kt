package com.ycr.lib.permission

import java.io.Serializable

/**
 * Created by yuchengren on 2019/3/14.
 */
class PermissionModule(var title: String?, var content: String?, vararg val actionArray: PermissionAction): Serializable