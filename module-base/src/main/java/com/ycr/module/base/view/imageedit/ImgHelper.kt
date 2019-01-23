package com.ycr.module.base.view.imageedit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.RectF
import android.os.Environment
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 * Created by yuchengren on 2018/11/16.
 */
object ImgHelper {

    fun getFitHomingValues(winRectF: RectF, imgRectF: RectF): ImgHomingValues {
        val imageRectF = RectF(imgRectF)
        val homingValues = ImgHomingValues()
        if (imageRectF.contains(winRectF)) {
            return homingValues
        }

        if (imageRectF.width() < winRectF.width() && imageRectF.height() < winRectF.height()) {
            homingValues.scale = Math.min(winRectF.width() / imageRectF.width(), winRectF.height() / imageRectF.height())
            Matrix().apply {
                setScale(homingValues.scale, homingValues.scale, imageRectF.centerX(), imageRectF.centerY())
            }.mapRect(imageRectF)
        }

        if (imageRectF.width() <= winRectF.width()) {
            homingValues.x += winRectF.centerX() - imageRectF.centerX()
        } else {
            if (imageRectF.left > winRectF.left) {
                homingValues.x += winRectF.left - imageRectF.left
            } else if (imageRectF.right < winRectF.right) {
                homingValues.x += winRectF.right - imageRectF.right
            }
        }

        if (imageRectF.height() <= winRectF.height()) {
            homingValues.y += winRectF.centerY() - imageRectF.centerY()
        } else {
            if(imageRectF.top > winRectF.top){
                homingValues.y += winRectF.top - imageRectF.top
            }else if(imageRectF.bottom < winRectF.bottom){
                homingValues.y += winRectF.bottom - imageRectF.bottom
            }
        }

        return homingValues
    }

    fun save(bitmap: Bitmap, filePath: String) {
        val file = File(filePath)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs() // 创建文件夹
        }
        try {
            val bos = BufferedOutputStream(FileOutputStream(file))
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos) // 向缓冲区之中压缩图片
            bos.flush()
            bos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getTempSavePath(context: Context): String{
        val appRootPath: String
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            appRootPath = Environment.getExternalStorageDirectory().absolutePath + "/" + context.getPackageName()
        } else {
            appRootPath = context.getFilesDir().getPath()
        }
        return appRootPath + "/temp/"+ System.currentTimeMillis().toString() + ".jpg"
    }


}