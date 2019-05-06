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

    private val M = Matrix()

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

    fun getFitHomingValues(win: RectF, frame: RectF, centerX: Float, centerY: Float): ImgHomingValues {
        val dHoming = ImgHomingValues()

        if (frame.contains(win)) {
            // 不需要Fit
            return dHoming
        }

        // 宽高都小于Win，才有必要放大
        if (frame.width() < win.width() && frame.height() < win.height()) {
            dHoming.scale = Math.min(win.width() / frame.width(), win.height() / frame.height())
        }

        val rect = RectF()
        M.setScale(dHoming.scale, dHoming.scale, centerX, centerY)
        M.mapRect(rect, frame)

        if (rect.width() < win.width()) {
            dHoming.x += win.centerX() - rect.centerX()
        } else {
            if (rect.left > win.left) {
                dHoming.x += win.left - rect.left
            } else if (rect.right < win.right) {
                dHoming.x += win.right - rect.right
            }
        }

        if (rect.height() < win.height()) {
            dHoming.y += win.centerY() - rect.centerY()
        } else {
            if (rect.top > win.top) {
                dHoming.y += win.top - rect.top
            } else if (rect.bottom < win.bottom) {
                dHoming.y += win.bottom - rect.bottom
            }
        }

        return dHoming
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

    fun getFillHomingValues(win: RectF, frame: RectF,pivotX: Float,pivotY: Float): ImgHomingValues{
        val dHoming = ImgHomingValues()
        if (frame.contains(win)) {
            // 不需要Fill
            return dHoming
        }

        if (frame.width() < win.width() || frame.height() < win.height()) {
            dHoming.scale = Math.max(win.width() / frame.width(), win.height() / frame.height())
        }

        val rect = RectF()
        M.setScale(dHoming.scale, dHoming.scale, pivotX, pivotY)
        M.mapRect(rect, frame)

        if (rect.left > win.left) {
            dHoming.x += win.left - rect.left
        } else if (rect.right < win.right) {
            dHoming.x += win.right - rect.right
        }

        if (rect.top > win.top) {
            dHoming.y += win.top - rect.top
        } else if (rect.bottom < win.bottom) {
            dHoming.y += win.bottom - rect.bottom
        }
        return dHoming
    }

    fun center(win: RectF, frame: RectF){
        frame.offset(win.centerX() - frame.centerX(),win.centerY() - frame.centerY())
    }

    fun fitCenter(win: RectF, frame: RectF) {
        fitCenter(win, frame, 0f)
    }

    fun fitCenter(win: RectF, frame: RectF, padding: Float) {
        fitCenter(win, frame, padding, padding, padding, padding)
    }

    fun fitCenter(win: RectF, frame: RectF, paddingLeft: Float, paddingTop: Float, paddingRight: Float, paddingBottom: Float) {
        var paddingLeft = paddingLeft
        var paddingTop = paddingTop
        var paddingRight = paddingRight
        var paddingBottom = paddingBottom
        if (win.isEmpty || frame.isEmpty) {
            return
        }

        if (win.width() < paddingLeft + paddingRight) {
            paddingRight = 0f
            paddingLeft = paddingRight
            // 忽略Padding 值
        }

        if (win.height() < paddingTop + paddingBottom) {
            paddingBottom = 0f
            paddingTop = paddingBottom
            // 忽略Padding 值
        }

        val w = win.width() - paddingLeft - paddingRight
        val h = win.height() - paddingTop - paddingBottom

        val scale = Math.min(w / frame.width(), h / frame.height())

        // 缩放FIT
        frame.set(0f, 0f, frame.width() * scale, frame.height() * scale)

        // 中心对齐
        frame.offset(
                win.centerX() + (paddingLeft - paddingRight) / 2 - frame.centerX(),
                win.centerY() + (paddingTop - paddingBottom) / 2 - frame.centerY()
        )
    }

    fun fill(win: RectF, frame: RectF): ImgHomingValues {
        val dHoming = ImgHomingValues()

        if (win == frame) {
            return dHoming
        }

        // 第一次时缩放到裁剪区域内
        dHoming.scale = Math.max(win.width() / frame.width(), win.height() / frame.height())

        val rect = RectF()
        M.setScale(dHoming.scale, dHoming.scale, frame.centerX(), frame.centerY())
        M.mapRect(rect, frame)

        dHoming.x += win.centerX() - rect.centerX()
        dHoming.y += win.centerY() - rect.centerY()

        return dHoming
    }


}