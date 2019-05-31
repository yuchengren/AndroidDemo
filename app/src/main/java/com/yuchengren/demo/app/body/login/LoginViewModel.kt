package com.yuchengren.demo.app.body.login

import android.databinding.ObservableField
import com.ycr.module.framework.mvvm.BaseViewModel

/**
 * created by yuchengren on 2019/5/28
 */
class LoginViewModel: BaseViewModel(){

    val userName = ObservableField<String?>("")
    val password = ObservableField<String?>("")

}