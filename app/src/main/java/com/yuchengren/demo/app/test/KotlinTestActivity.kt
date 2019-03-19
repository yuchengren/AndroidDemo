package com.yuchengren.demo.app.test

import android.content.res.Configuration
import android.graphics.Color
import android.graphics.RectF
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.bumptech.glide.Glide
import com.ycr.module.base.BaseActivity
import com.yuchengren.demo.R
import kotlinx.android.synthetic.main.activity_kotlin_test.*

/**
 * Created by yuchengren on 2019/1/28.
 */
class KotlinTestActivity: BaseActivity() {
    private var clipRowSpans: Int = 3
    private var clipColumnSpans: Int = 3
    private var clipCornerWidth: Int = 10
    private var clipCornerLineWidth: Int = 1
    private val clipRectF = RectF(0f,0f,150f,90f)
    override fun getRootLayoutResId(): Int {
        return R.layout.activity_kotlin_test
    }

    override fun afterBindView(rootView: View?, savedInstanceState: Bundle?) {
        super.afterBindView(rootView, savedInstanceState)

//        Glide.with(this).load(Environment.getExternalStorageDirectory().path + "/test.png").into(imageView)
        Glide.with(this).load("").into(imageView)

        btn_test.setBackgroundColor(Color.parseColor("#FF0000"))
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }
}