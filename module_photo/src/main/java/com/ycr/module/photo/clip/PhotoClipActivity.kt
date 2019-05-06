package com.ycr.module.photo.clip

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import com.ycr.kernel.util.deleteFile
import com.ycr.module.base.BaseActivity
import com.ycr.module.base.view.imageedit.ImgHelper
import com.ycr.module.photo.R
import com.ycr.module.photo.chosen.ChosenPhotoActivity
import com.ycr.module.photo.constants.IConstants
import com.ycr.module.photo.constants.MapKeys
import kotlinx.android.synthetic.main.activity_photo_clip.*
import java.io.File

/**
 * Created by yuchengren on 2019/1/25.
 */
class PhotoClipActivity: BaseActivity() {

    companion object {
        fun start(context: Context, picUrl: String,picSource: String? = null){
            val intent = Intent(context, PhotoClipActivity::class.java).apply {
                putExtra(MapKeys.URL_PIC,picUrl)
                putExtra(MapKeys.PIC_SOURCE,picSource)
            }
            context.startActivity(intent)
        }
    }

    private var originPicUrl: String? = null
    private var picSource: String? = null

    override fun getRootLayoutResId(): Int {
        return R.layout.activity_photo_clip
    }

    override fun afterBindView(rootView: View?, savedInstanceState: Bundle?) {
        super.afterBindView(rootView, savedInstanceState)

        originPicUrl = intent.getStringExtra(MapKeys.URL_PIC)
        picSource = intent.getStringExtra(MapKeys.PIC_SOURCE)
        if(!originPicUrl.isNullOrEmpty()){
            photoEditView.setImageBitmap( BitmapFactory.decodeFile(originPicUrl))
        }

        cancelView.setOnClickListener { onBackPressed() }

        confirmView.setOnClickListener {
            val bitmap = photoEditView.save()?:return@setOnClickListener
            val tempSavePath = ImgHelper.getTempSavePath(this@PhotoClipActivity)
            ImgHelper.save(bitmap,tempSavePath)
            if(IConstants.PicSource.PHOTO_CAMERA == picSource){
                File(originPicUrl).deleteFile()
            }
            ChosenPhotoActivity.start(this@PhotoClipActivity,tempSavePath)
        }

        rotateView.setOnClickListener {  photoEditView.rotate()}
    }
}