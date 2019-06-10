package com.ycr.module.photo.chosen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.ycr.lib.permission.IPermissionResult
import com.ycr.lib.permission.PermissionAction
import com.ycr.lib.permission.PermissionHelper
import com.ycr.lib.permission.PermissionModule
import com.ycr.module.base.BaseActivity
import com.ycr.module.base.permission.PermissionModuleActivity
import com.ycr.module.base.util.ToastHelper
import com.ycr.module.photo.R
import com.ycr.module.photo.camera.SubjectPhotoTakeActivity
import com.ycr.module.photo.constants.MapKeys
import com.ycr.module.photo.grid.PhotoBookGirdActivity
import kotlinx.android.synthetic.main.activity_chosen_photo.*

/**
 * Created by yuchengren on 2019/1/21.
 */
class ChosenPhotoActivity: BaseActivity() {

    companion object {

        const val REQUEST_CODE_PERMISSION = 1

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
            if(!PermissionHelper.checkPermissions(this,Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                val permissionModule = PermissionModule("拍摄照片","允许访问即可进入拍摄",
                        PermissionAction(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        PermissionAction(Manifest.permission.CAMERA))
                PermissionHelper.startForResult(this@ChosenPhotoActivity,
                        PermissionModuleActivity::class.java,REQUEST_CODE_PERMISSION,permissionModule)
                return@setOnClickListener
            }


            SubjectPhotoTakeActivity.start(this@ChosenPhotoActivity)
//            PhotoClipActivity.start(this@ChosenPhotoActivity,Environment.getExternalStorageDirectory().path + "/test.png")
        }
        photoChoose.setOnClickListener {
            PhotoBookGirdActivity.start(this)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.getStringExtra(MapKeys.URL_PIC)?.apply {
            Glide.with(this@ChosenPhotoActivity).load(this).into(photo)
//            photo.setImageBitmap(BitmapFactory.decodeFile("/storage/emulated/0/DCIM/Camera/IMG_20190128_150500.jpg"))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        PermissionHelper.handleRequestResult(resultCode,object: IPermissionResult {
            override fun onGranted() {
                SubjectPhotoTakeActivity.start(this@ChosenPhotoActivity)
            }

            override fun onDefined() {
                ToastHelper.show("缺少拍照所需权限")
            }

        })
    }
}