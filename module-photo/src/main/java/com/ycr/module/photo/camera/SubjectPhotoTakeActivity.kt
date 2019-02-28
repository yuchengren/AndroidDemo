package com.ycr.module.photo.camera

import android.content.Context
import android.content.Intent
import com.ycr.module.photo.R
import com.ycr.module.photo.constants.IConstants
import com.ycr.module.photo.preview.PreviewPhotoActivity

/**
 * Created by yuchengren on 2019/1/25.
 */
class SubjectPhotoTakeActivity: TakePhotoActivity() {

    companion object {
        fun start(context: Context){
            val intent = Intent(context,SubjectPhotoTakeActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getRootLayoutResId(): Int {
        return R.layout.activity_subject_photo_take
    }

    override fun onSavePictureSuccess(picUrl: String) {
        PreviewPhotoActivity.start(this, picUrl,IConstants.PicSource.PHOTO_CAMERA)
    }

}