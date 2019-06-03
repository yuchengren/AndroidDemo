package com.yuchengren.demo.app.body.login

import android.app.Application
import android.databinding.ObservableField
import android.view.View
import com.ycr.kernel.task.AbstractTask
import com.ycr.kernel.union.task.CommonTask
import com.ycr.module.base.util.ToastHelper
import com.ycr.module.framework.mvvm.BaseViewModel

/**
 * created by yuchengren on 2019/5/28
 */
class LoginViewModel(application: Application): BaseViewModel(application){

    val userName = ObservableField<String?>("")
    val password = ObservableField<String?>("")

    fun login(view: View){


    }

    fun onViewClick(tag: String){
        submitTask(object : CommonTask<UserEntity>(){

            override fun onBeforeCall(task: AbstractTask<UserEntity>) {
                super.onBeforeCall(task)
                showLoading("")
            }

            override fun onAfterCall() {
                super.onAfterCall()
                dismissLoading()
            }

            override fun doInBackground(): UserEntity {
                Thread.sleep(1000)

                return UserEntity().apply {
                    account = userName.get()
                    password = this@LoginViewModel.password.get()
                }
            }

            override fun onCompleted(data: UserEntity) {
                super.onCompleted(data)
                ToastHelper.show("登录成功，account=${userName.get()},password=${password.get()}")
            }

        })
    }

    override fun onResume() {
        super.onResume()
        ToastHelper.show(TAG +"onResume")
    }
}