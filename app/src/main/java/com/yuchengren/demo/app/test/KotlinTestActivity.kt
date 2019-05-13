package com.yuchengren.demo.app.test

import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.view.View
import com.ycr.kernel.union.helper.ImageHelper
import com.ycr.lib.theme.MessageDialogButtonStyle
import com.ycr.lib.ui.dialog.MessageDialogFragment
import com.ycr.module.base.BaseActivity
import com.ycr.module.base.constant.ImageOptions
import com.ycr.module.base.util.ToastHelper
import com.yuchengren.demo.R
import kotlinx.android.synthetic.main.activity_kotlin_test.*

/**
 * Created by yuchengren on 2019/1/28.
 */
class KotlinTestActivity: BaseActivity() {

    override fun getRootLayoutResId(): Int {
        return R.layout.activity_kotlin_test
    }

    override fun afterBindView(rootView: View?, savedInstanceState: Bundle?) {
        super.afterBindView(rootView, savedInstanceState)

        ImageHelper.display(imageView,Environment.getExternalStorageDirectory().path + "/test.png", ImageOptions.default)
        btn_test.setOnClickListener {
            MessageDialogFragment.builder().
                    title("").
                    contentText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").
                    contextTextGravity(Gravity.CENTER).
                    contentTextAlignment(View.TEXT_ALIGNMENT_CENTER)
                    .buttonTexts("取消","确定")
                    .buttonStyles(MessageDialogButtonStyle.DEFAULT,MessageDialogButtonStyle.STRONG)
                    .build().setOnButtonClickListener(object: MessageDialogFragment.OnButtonClickListener{
                        override fun onButtonClick(dialog: MessageDialogFragment, text: String, textResId: Int, position: Int) {
                            ToastHelper.show(text)
                        }
                    }).show(supportFragmentManager,"tag")

        }
    }




}