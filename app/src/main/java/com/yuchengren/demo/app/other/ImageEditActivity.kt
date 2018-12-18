package com.yuchengren.demo.app.other

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import com.yuchengren.demo.R

import com.yuchengren.demo.view.imageedit.ImgHelper
import com.yuchengren.demo.util.ToastHelper
import kotlinx.android.synthetic.main.activity_image_edit.*
import java.io.File

/**
 * Created by yuchengren on 2018/11/16.
 */
class ImageEditActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_edit)
        imageEditView.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.test))

//        imageEditView.pathList.add(GraffitiPath(GraffitiPath.parseSVGStringToPath(CompressUtils.uncompressString("H4sIAAAAAAAAAD1QyREDMQhrCGYs8LU9Kf1/I9hJPpLBHEKfGMeASDrM8cMwj343jgIwLIlhl0i1\n" +
//                "    MLbhcoZFcG0LcD2GwT3NJ/djfivjSO4hev5Ucxen/uJhXhGYaZ6DqGgTS3S5ldNO4asmN73qJl11\n" +
//                "    uZRcPcSxXw6tiCquxVexynHEEgW1JUqW59Nn5qnb6gThNM2bOkpezPUSmiQR0U09YjTFsVBzqLLm\n" +
//                "    x0vDysqAxenlWd5dS7TEVjr6T4GOOWWbT0NJ/+Fpu5O9J+ulgsVlMsviC5TV+QyyAQAA"))))

//        imageEditView.pathList.add(GraffitiPath(GraffitiPath.parseSVGStringToPath("m35,33l0,1l-1,0l0,2l0,2l0,1l1,1l7,1l14,1l28,3l33,3l36,0l32,0l33,2l30,2l24,3l22,3l21,0l18,-2l17,-4l17,-3l17,-4l16,-4l14,-1l13,-1l10,2l9,1l9,2l8,0l11,1l7,0l6,0l6,0l5,0l6,0l3,0l2,0l0,1l0,1l1,0l1,0l0,1l1,0l1,0l-1,0l0,1l0,1l0,1l-2,2l-2,2l-1,2l-3,2l-3,3l-3,3l-4,3l-9,8l-16,13l-22,19l-23,19l-20,19l-20,21l-19,18l-17,18l-15,17l-14,15l-12,16l-13,15l-11,16l-14,18l-16,16l-14,18l-15,14l-14,16l-15,15l-14,15l-11,14l-15,15l-13,17l-13,17l-13,17l-12,17l-13,16l-10,17l-7,14l-7,13l-7,12l-6,11l-3,9l-4,8l-4,7l-2,6l-2,5l-4,7l-2,8l-2,7l-2,7l-3,6l-1,6l-1,6l-1,5l-1,6l-1,5l-1,6l-1,6l-1,6l-2,5l0,3l-1,3l-1,2l-1,1l-2,1l0,1l0,1l1,0l1,0l2,-1l2,0l4,-2l5,-1l11,-3l18,-4l24,-5l27,-5l22,-4l16,0l16,-1l16,1l13,1l14,0l15,0l16,-1l15,-2l14,-2l16,-1l12,-2l14,-2l12,1l10,-1l14,1l12,1l8,0l12,1l11,1l8,0l8,1l11,1l9,0l7,1l6,1l6,0l5,0l6,1l6,1l6,0l6,1l5,0l5,0l4,0l3,0l1,0l1,0l2,-1l1,0l2,0l1,0l2,0l2,0l2,0l4,0l3,0l6,0l2,0")))
        imageEditView.invalidate()

        btn_test1.setOnClickListener {
            imageEditView.clear()
            val a  = mutableListOf<Int>(1,2,3,4,5)
            val b = mutableListOf<Int>(2,3)
            b.forEach outer@{item ->
               val iterator = a.iterator()
                while (iterator.hasNext()){
                    if(iterator.next() == item){
                        iterator.remove()
                    }
                }
            }
            println(a)
        }
        btn_test2.setOnClickListener { imageEditView.undo() }
        btn_test3.setOnClickListener {
            val bitmap = imageEditView.save()
            val file = File(Environment.getExternalStorageDirectory(), System.currentTimeMillis().toString() + ".jpg")
            ImgHelper.save(bitmap?:return@setOnClickListener,file.path)
            ToastHelper.show("保存完成")
        }



    }




}