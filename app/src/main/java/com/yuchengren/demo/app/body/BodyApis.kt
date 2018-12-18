package com.yuchengren.demo.app.body

import com.ycr.module.framework.http.Api
import com.yuchengren.demo.app.body.login.UserEntity

/**
 * Created by yuchengren on 2018/12/18.
 */
interface BodyApis{
    companion object {
        val login = Api.POST("member.login", UserEntity::class.java)
    }
}