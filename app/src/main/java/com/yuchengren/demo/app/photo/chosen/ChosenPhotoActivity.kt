package com.yuchengren.demo.app.photo.chosen

import android.os.Bundle
import android.view.View
import com.yuchengren.demo.R
import com.yuchengren.demo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_chosen_photo.*

/**
 * Created by yuchengren on 2019/1/21.
 */
class ChosenPhotoActivity: BaseActivity() {

    override fun getRootLayoutResId(): Int {
        return R.layout.activity_chosen_photo
    }

    override fun afterBindView(rootView: View?, savedInstanceState: Bundle?) {
        super.afterBindView(rootView, savedInstanceState)
        cameraGet.setOnClickListener {

        }
        photoChoose.setOnClickListener {

        }
    }
}