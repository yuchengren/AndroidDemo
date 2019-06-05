package com.yuchengren.demo.app.body.login

import android.app.Application
import com.ycr.kernel.task.AbstractTask
import com.ycr.kernel.union.task.CommonTask
import com.ycr.module.base.util.ToastHelper
import com.ycr.module.framework.mvvm.viewmodel.BaseViewModel

/**
 * created by yuchengren on 2019-05-31
 */
class UserViewModel(application: Application): BaseViewModel(application) {


    fun getUserDetail(userId: String?){

        submitTask(object : CommonTask<UserDetailEntity>(){

            override fun onBeforeCall(task: AbstractTask<UserDetailEntity>) {
                super.onBeforeCall(task)
                showLoading("")
            }

            override fun onAfterCall() {
                super.onAfterCall()
                dismissLoading()
            }

            override fun doInBackground(): UserDetailEntity {
                Thread.sleep(1000)

                return UserDetailEntity().apply {
                    nickName = "牧心"
                }
            }

            override fun onCompleted(data: UserDetailEntity) {
                super.onCompleted(data)
                ToastHelper.show("获取用户明细成功，nickName=${data.nickName}")
            }

        })
    }
}