package com.yuchengren.demo.app.body.login

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.ycr.module.base.MvvmActivity
import com.yuchengren.demo.BR
import com.yuchengren.demo.R
import com.yuchengren.demo.databinding.ActivityLoginBinding

/**
 * Created by yuchengren on 2016/9/14.
 */
class LoginActivity : MvvmActivity<ActivityLoginBinding>(), ILoginContract.IView {

    //    ILoginContract.IPresenter presenter;

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context,LoginActivity::class.java))
        }
    }

    override fun getRootLayoutResId(): Int {
        return R.layout.activity_login
    }

    override fun afterBindView(rootView: View?, savedInstanceState: Bundle?) {
        super.afterBindView(rootView, savedInstanceState)
        //        presenter = new LoginPresenter(this);
        setViewModel(BR.LoginViewModel,LoginViewModel::class.java)
        setViewModel(BR.UserViewModel,UserViewModel::class.java)

//        viewDataBinding.loginViewModel?.userName
        getViewModel(LoginViewModel::class.java).userName

        viewDataBinding.loginViewModel?.pwdShowSwitchEvent?.observe(this, Observer{
            if(it == true){
                viewDataBinding.ivPwdShowSwitch.setImageResource(R.mipmap.show_psw)
                viewDataBinding.etPwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                viewDataBinding.ivPwdShowSwitch.setImageResource(R.mipmap.show_psw_press)
                viewDataBinding.etPwd.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        })

        viewDataBinding.loginViewModel?.userEntityLiveData?.observe(this, Observer {
            viewDataBinding.userViewModel?.getUserDetail(it?.userId)
        })

    }

    override fun onLoginSuccess() {

    }

    override fun onLoginFail() {

    }
}
