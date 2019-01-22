package com.yuchengren.demo.app.photo.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import android.hardware.Camera
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.view.SurfaceView
import android.view.View
import com.ycr.kernel.http.IResult
import com.ycr.kernel.union.task.Result
import com.ycr.module.framework.task.ApiTask
import com.ycr.module.framework.task.SimpleResult
import com.yuchengren.demo.R
import com.yuchengren.demo.view.imageedit.ImgHelper

/**
 * Created by yuchengren on 2019/1/21.
 */
class TakePhotoActivity: CameraBaseActivity() {

    var photoClipFrameView: View? = null

    override fun getRootLayoutResId(): Int {
        return R.layout.activity_camera
    }

    override fun bindView(rootView: View?) {
        super.bindView(rootView)
        photoClipFrameView = findViewById(R.id.flashView)
    }

    override fun startTake() {
        playTakeSound()
        camera?.takePicture(null,null,object: Camera.PictureCallback{
            override fun onPictureTaken(data: ByteArray?, camera: Camera?) {
                if(data == null){
                    return
                }
                savePicture(data)
            }

        })

    }

    private fun savePicture(data: ByteArray) {
        val apiTask = object: ApiTask<String>(){
            override fun doInBackground(): IResult<String> {
                var bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                if(cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT){
                    val matrix = Matrix().apply { setScale(-1f,1f) }
                    bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.width,bitmap.height,matrix,true)
                }
                photoClipFrameView?.run {
                    bitmap = getClipBitmap(bitmap,this,cameraView?:return@run)
                }
                val tempSavePath = ImgHelper.getTempSavePath(this@TakePhotoActivity)
                ImgHelper.save(bitmap,tempSavePath)
                return SimpleResult.success(null,null,tempSavePath)
            }

            override fun onSuccess(result: IResult<String>) {
                super.onSuccess(result)

            }

        }
    }

    private fun getClipBitmap(bitmap: Bitmap,photoClipFrameView: View,cameraView: SurfaceView): Bitmap{
        val widthRate = bitmap.width / cameraView.measuredWidth.toFloat()
        val heightRate = bitmap.height / cameraView.measuredHeight.toFloat()
        val clipLeft = (photoClipFrameView.x - cameraView.x) * widthRate
        val clipTop = (photoClipFrameView.y - cameraView.y) * heightRate
        val clipWidth = photoClipFrameView.measuredWidth * widthRate
        val clipHeight = photoClipFrameView.measuredHeight * heightRate
        return Bitmap.createBitmap(bitmap,clipLeft.toInt(),clipTop.toInt(),clipWidth.toInt(),clipHeight.toInt())
    }

    private fun playTakeSound() {
        val audioManager: AudioManager? = getSystemService(Context.AUDIO_SERVICE) as? AudioManager
        audioManager?.run {
            val volume = getStreamVolume(AudioManager.STREAM_NOTIFICATION)
            if(volume > 0){
                if(takeSoundPlayer == null){
                    takeSoundPlayer = MediaPlayer.create(this@TakePhotoActivity, Uri.parse("file:///system/media/audio/ui/camera_click.ogg"))
                }
                takeSoundPlayer?.start()
            }
        }
    }
}