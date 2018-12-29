package com.yuchengren.demo.app.other

import android.os.Bundle
import android.os.Environment
import android.view.View
import com.bumptech.glide.Glide
import com.ycr.module.framework.base.SuperActivity
import com.yuchengren.demo.R
import com.yuchengren.demo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_call_phone_back.view.*
import kotlinx.android.synthetic.main.activity_nine_grid.*
import java.io.File

/**
 * Created by yuchengren on 2018/12/27.
 */
class NineGridActivity: BaseActivity() {


    override fun getRootLayoutResId(): Int {
        return R.layout.activity_nine_grid
    }

    override fun afterBindView(rootView: View?, savedInstanceState: Bundle?) {
        super.afterBindView(rootView, savedInstanceState)
        rootView?.postDelayed({
//            Glide.with(this).load(R.drawable.ic_plus_grid_view).into(gridImageView)
            gridImageView.setImageResource(R.drawable.test)
        },100)

    }
}