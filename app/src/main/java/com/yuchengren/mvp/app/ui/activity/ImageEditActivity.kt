package com.yuchengren.mvp.app.ui.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.yuchengren.mvp.R
import com.yuchengren.mvp.app.ui.view.imageedit.GraffitiPath

import com.yuchengren.mvp.app.ui.view.imageedit.ImgHelper
import com.yuchengren.mvp.util.CompressUtils
import com.yuchengren.mvp.util.ToastHelper
import kotlinx.android.synthetic.main.activity_image_edit.*
import java.io.File

/**
 * Created by yuchengren on 2018/11/16.
 */
class ImageEditActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_edit)
        imageEditView.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.port))

//        imageEditView.pathList.add(GraffitiPath(GraffitiPath.parseSVGStringToPath(CompressUtils.uncompressString("H4sIAAAAAAAAAD1QyREDMQhrCGYs8LU9Kf1/I9hJPpLBHEKfGMeASDrM8cMwj343jgIwLIlhl0i1\n" +
//                "    MLbhcoZFcG0LcD2GwT3NJ/djfivjSO4hev5Ucxen/uJhXhGYaZ6DqGgTS3S5ldNO4asmN73qJl11\n" +
//                "    uZRcPcSxXw6tiCquxVexynHEEgW1JUqW59Nn5qnb6gThNM2bOkpezPUSmiQR0U09YjTFsVBzqLLm\n" +
//                "    x0vDysqAxenlWd5dS7TEVjr6T4GOOWWbT0NJ/+Fpu5O9J+ulgsVlMsviC5TV+QyyAQAA"))))
        imageEditView.invalidate()

        btn_test1.setOnClickListener { imageEditView.clear() }
        btn_test2.setOnClickListener { imageEditView.undo() }
        btn_test3.setOnClickListener {
            val bitmap = imageEditView.save()
            val file = File(Environment.getExternalStorageDirectory(), System.currentTimeMillis().toString() + ".jpg")
            ImgHelper.save(bitmap?:return@setOnClickListener,file.path)
            ToastHelper.show("保存完成")
        }

    }




}