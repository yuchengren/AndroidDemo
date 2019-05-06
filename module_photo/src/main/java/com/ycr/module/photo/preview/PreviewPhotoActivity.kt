package com.ycr.module.photo.preview

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import com.ycr.module.base.BaseActivity
import com.ycr.module.photo.R
import com.ycr.module.photo.clip.PhotoClipActivity
import com.ycr.module.photo.constants.IConstants
import com.ycr.module.photo.constants.MapKeys
import kotlinx.android.synthetic.main.activity_preview_photo.*
import java.io.File

/**
 * Created by yuchengren on 2019/1/24.
 */
class PreviewPhotoActivity: BaseActivity() {

    companion object {
        fun start(context: Context, picUrl: String,picSource: String? = null){
            val intent = Intent(context, PreviewPhotoActivity::class.java).apply {
                putExtra(MapKeys.URL_PIC,picUrl)
                putExtra(MapKeys.PIC_SOURCE,picSource)
            }
            context.startActivity(intent)
        }
    }
    private var picUrl: String? = null
    private var picSource: String? = null

    override fun getRootLayoutResId(): Int {
        return R.layout.activity_preview_photo
    }

    override fun afterBindView(rootView: View?, savedInstanceState: Bundle?) {
        super.afterBindView(rootView, savedInstanceState)
        picUrl = intent.getStringExtra(MapKeys.URL_PIC)
        picSource = intent.getStringExtra(MapKeys.PIC_SOURCE)
        if(!picUrl.isNullOrEmpty()){
            photoEditView.setImageBitmap( BitmapFactory.decodeFile(picUrl))
        }
        cancelView.setOnClickListener { onBackPressed() }

        confirmView.setOnClickListener {  PhotoClipActivity.start(this@PreviewPhotoActivity,picUrl?:return@setOnClickListener,picSource)}
    }

    override fun onBackPressed() {
        if(!picUrl.isNullOrEmpty() && IConstants.PicSource.PHOTO_CAMERA == picSource){
            File(picUrl).delete()
        }
        finish()
    }
}