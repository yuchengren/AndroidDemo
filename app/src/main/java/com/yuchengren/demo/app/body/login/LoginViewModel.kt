package com.yuchengren.demo.app.body.login

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.databinding.Observable
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.databinding.adapters.TextViewBindingAdapter
import android.text.TextUtils
import android.view.View
import com.ycr.kernel.log.LogHelper
import com.ycr.kernel.task.AbstractTask
import com.ycr.kernel.union.task.CommonTask
import com.ycr.module.base.util.ToastHelper
import com.ycr.module.framework.mvvm.viewmodel.BaseViewModel
import com.ycr.module.framework.mvvm.binding.command.BindingCommand
import com.ycr.module.framework.mvvm.binding.command.BindingInCommand
import com.ycr.module.framework.mvvm.binding.command.InAction
import com.ycr.module.framework.mvvm.binding.command.VoidAction
import com.ycr.module.framework.mvvm.event.SingleLiveData

/**
 * created by yuchengren on 2019/5/28
 */
class LoginViewModel(application: Application): BaseViewModel(application){

    val onFocusChange = View.OnFocusChangeListener { v, hasFocus ->
        LogHelper.e("onFocusChange")
    }

    val beforeTextChanged = TextViewBindingAdapter.BeforeTextChanged { s, start, count, after ->
        LogHelper.e("beforeTextChanged")
    }

    val onClickListener = View.OnClickListener{
        LogHelper.e("onClickListener")
    }

    val userName = ObservableField<String?>("").apply {
        addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                clearUserNameViewVisibility.set(if(TextUtils.isEmpty(get())) View.INVISIBLE else View.VISIBLE)
            }
        })
    }
    val password = ObservableField<String?>("")

    //清除用户名的图标的可见性
    val clearUserNameViewVisibility = ObservableInt(View.INVISIBLE)

    //清除用户名
    val clearUserNameOnClickCommand = BindingCommand(VoidAction {
        userName.set("")
    })

    //用户名控件焦点改变事件
    val onUserNameFocusChangeCommand = BindingInCommand<Boolean>(InAction {
        clearUserNameViewVisibility.set(if(!it) View.INVISIBLE else View.VISIBLE)
    })


    //密码显示开关点击事件
    val onPwdShowSwitchOnClickCommand = BindingCommand(VoidAction {
        pwdShowSwitchEvent.value = (pwdShowSwitchEvent.value?:false).not()
    })

    //密码显示开关观察者
    val pwdShowSwitchEvent = SingleLiveData<Boolean>()

    val userEntityLiveData = MutableLiveData<UserEntity?>()

    fun login(view: View){}

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
                    userId = "10001"
                }
            }

            override fun onCompleted(data: UserEntity) {
                super.onCompleted(data)
                ToastHelper.show("登录成功，account=${userName.get()},password=${password.get()}")
                userEntityLiveData.postValue(data)
            }

        })
    }

    override fun onResume() {
        super.onResume()
        ToastHelper.show(TAG +"onResume")
    }
}