package com.ycr.module.photo.clip

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import com.ycr.module.base.BaseActivity
import com.ycr.module.base.view.imageedit.ImgHelper
import com.ycr.module.photo.R
import com.ycr.module.photo.constants.MapKeys
import kotlinx.android.synthetic.main.activity_photo_clip.*

/**
 * Created by yuchengren on 2019/1/25.
 */
class PhotoClipActivity: BaseActivity() {

    companion object {
        fun start(context: Context, picUrl: String){
            val intent = Intent(context, PhotoClipActivity::class.java).apply {
                putExtra(MapKeys.URL_PIC,picUrl)
            }
            context.startActivity(intent)
        }
    }

    override fun getRootLayoutResId(): Int {
        return R.layout.activity_photo_clip
    }

    override fun afterBindView(rootView: View?, savedInstanceState: Bundle?) {
        super.afterBindView(rootView, savedInstanceState)

        val picUrl = intent.getStringExtra(MapKeys.URL_PIC)
        if(!picUrl.isNullOrEmpty()){
            photoEditView.setImageBitmap( BitmapFactory.decodeFile(picUrl))
        }

        cancelView.setOnClickListener { onBackPressed() }

        confirmView.setOnClickListener {
            val bitmap = photoEditView.save()?:return@setOnClickListener
            val tempSavePath = ImgHelper.getTempSavePath(this@PhotoClipActivity)
            ImgHelper.save(bitmap,tempSavePath)
//            ChosenPhotoActivity.start(this@PhotoClipActivity,tempSavePath)
        }

        rotateView.setOnClickListener {  photoEditView.rotate()}
    }
}