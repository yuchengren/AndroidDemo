package com.yuchengren.demo.app.test

import android.content.res.Configuration
import android.graphics.Color
import android.graphics.RectF
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.bumptech.glide.Glide
import com.ycr.kernel.image.ImageDisplayType
import com.ycr.kernel.image.glide.GlideImageLoader
import com.ycr.kernel.image.glide.ImageDisplayOption
import com.ycr.kernel.union.helper.ImageHelper
import com.ycr.kernel.union.helper.UnionContainer
import com.ycr.module.base.BaseActivity
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

        ImageHelper.display(imageView,Environment.getExternalStorageDirectory().path + "/test.png",
                ImageDisplayOption.build().cornerRadius(12).imageDisplayType(ImageDisplayType.CENTER_CROP))

    }


}