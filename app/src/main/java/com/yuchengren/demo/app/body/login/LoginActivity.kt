package com.yuchengren.demo.app.body.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import butterknife.OnClick
import com.ycr.module.base.AppActivity
import com.yuchengren.demo.BR
import com.yuchengren.demo.R
import com.yuchengren.demo.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by yuchengren on 2016/9/14.
 */
class LoginActivity : AppActivity<ActivityLoginBinding>(), ILoginContract.IView {

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

    }

    override fun onLoginSuccess() {

    }

    override fun onLoginFail() {

    }
}
