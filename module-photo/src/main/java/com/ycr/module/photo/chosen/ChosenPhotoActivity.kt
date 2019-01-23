package com.ycr.module.photo.chosen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.ycr.module.base.BaseActivity
import com.ycr.module.photo.R
import com.ycr.module.photo.camera.TakePhotoActivity
import com.ycr.module.photo.constants.MapKeys

import kotlinx.android.synthetic.main.activity_chosen_photo.*

/**
 * Created by yuchengren on 2019/1/21.
 */
class ChosenPhotoActivity: BaseActivity() {

    companion object {
        fun start(context: Context, url: String = ""){
            val intent = Intent(context, ChosenPhotoActivity::class.java).apply {
                putExtra(MapKeys.URL_PIC,url)
            }
            context.startActivity(intent)
        }
    }

    override fun getRootLayoutResId(): Int {
        return R.layout.activity_chosen_photo
    }

    override fun afterBindView(rootView: View?, savedInstanceState: Bundle?) {
        super.afterBindView(rootView, savedInstanceState)
        cameraGet.setOnClickListener {
            TakePhotoActivity.start(this@ChosenPhotoActivity)
        }
        photoChoose.setOnClickListener {

        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.getStringExtra(MapKeys.URL_PIC)?.apply {
            Glide.with(this@ChosenPhotoActivity).load(this).into(photo)
        }

    }
}