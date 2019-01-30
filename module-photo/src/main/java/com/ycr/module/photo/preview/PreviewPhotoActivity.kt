package com.ycr.module.photo.preview

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import com.ycr.module.base.BaseActivity
import com.ycr.module.photo.R
import com.ycr.module.photo.clip.PhotoClipActivity
import com.ycr.module.photo.constants.MapKeys
import kotlinx.android.synthetic.main.activity_preview_photo.*

/**
 * Created by yuchengren on 2019/1/24.
 */
class PreviewPhotoActivity: BaseActivity() {

    companion object {
        fun start(context: Context, picUrl: String, entrance: String){
            val intent = Intent(context, PreviewPhotoActivity::class.java).apply {
                putExtra(MapKeys.URL_PIC,picUrl)
                putExtra(MapKeys.ENTRANCE,entrance)
            }
            context.startActivity(intent)
        }
    }

    override fun getRootLayoutResId(): Int {
        return R.layout.activity_preview_photo
    }

    override fun afterBindView(rootView: View?, savedInstanceState: Bundle?) {
        super.afterBindView(rootView, savedInstanceState)
        val picUrl = intent.getStringExtra(MapKeys.URL_PIC)
        if(!picUrl.isNullOrEmpty()){
//            photoEditView.setImageBitmap( BitmapFactory.decodeFile(picUrl))
        }
        cancelView.setOnClickListener { onBackPressed() }

        confirmView.setOnClickListener {  PhotoClipActivity.start(this@PreviewPhotoActivity,picUrl)}
    }
}