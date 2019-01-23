package com.ycr.module.photo.chosen

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ycr.module.base.BaseActivity
import com.ycr.module.photo.R

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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)


    }
}