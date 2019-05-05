package com.yuchengren.demo.app.test

import android.os.Bundle
import android.os.Environment
import android.view.View
import com.ycr.kernel.image.CornerType
import com.ycr.kernel.image.ImageDisplayType
import com.ycr.kernel.image.glide.ImageDisplayOption
import com.ycr.kernel.union.helper.ImageHelper
import com.ycr.module.base.BaseActivity
import com.ycr.module.base.constant.ImageOptions
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

    }


}