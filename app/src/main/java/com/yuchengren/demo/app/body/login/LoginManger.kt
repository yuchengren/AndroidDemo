package com.yuchengren.demo.app.body.login

import com.ycr.kernel.http.IResult
import com.ycr.module.framework.http.BaseManager
import com.yuchengren.demo.app.body.BodyApis

/**
 * Created by yuchengren on 2019/2/28.
 */
class LoginManger: BaseManager() {

    fun <T> login(loginName: String,password: String): IResult<T>{
        val map = HashMap<String,Any>().apply {
            put("loginName", loginName)
            put("password", password)
        }
        return super.execute(BodyApis.login,map)
    }
}