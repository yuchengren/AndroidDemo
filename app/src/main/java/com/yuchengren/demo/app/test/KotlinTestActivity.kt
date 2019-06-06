package com.yuchengren.demo.app.test

import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.view.View
import com.ycr.kernel.log.LogHelper
import com.ycr.kernel.union.helper.ImageHelper
import com.ycr.kernel.util.NumberUtil
import com.ycr.lib.theme.MessageDialogButtonStyle
import com.ycr.lib.ui.dialog.MessageDialogFragment
import com.ycr.module.base.BaseActivity
import com.ycr.module.base.constant.ImageOptions
import com.ycr.module.base.util.ToastHelper
import com.yuchengren.demo.R
import com.yuchengren.demo.app.body.login.LoginActivity
import kotlinx.android.synthetic.main.activity_kotlin_test.*

/**
 * Created by yuchengren on 2019/1/28.
 */
class KotlinTestActivity: BaseActivity(),MessageDialogFragment.OnButtonClickListener {

    override fun getRootLayoutResId(): Int {
        return R.layout.activity_kotlin_test
    }

    override fun afterBindView(rootView: View?, savedInstanceState: Bundle?) {
        super.afterBindView(rootView, savedInstanceState)
//        ImageHelper.display(imageView,Environment.getExternalStorageDirectory().path + "/test.png", ImageOptions.default)
//        btn_test.setOnClickListener {
//            MessageDialogFragment.builder()
//                    .title("")
//                    .contentText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
//                    .buttonTextResIds(R.string.cancel,R.string.confirm)
//                    .buttonStyles(MessageDialogButtonStyle.WEAK,MessageDialogButtonStyle.STRONG)
//                    .build().show(supportFragmentManager,"tag")
//        }

        btn_test.setOnClickListener {
            LoginActivity.start(this)
        }
    }

    override fun onButtonClick(dialog: MessageDialogFragment, text: String, textResId: Int, position: Int): Boolean {
        when(textResId){
            R.string.confirm -> ToastHelper.show(text)//确定的业务逻辑
            R.string.cancel -> ToastHelper.show(text)//取消的业务逻辑
        }
        return true
    }
}