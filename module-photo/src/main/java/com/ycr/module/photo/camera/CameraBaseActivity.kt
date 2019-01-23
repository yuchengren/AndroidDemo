package com.ycr.module.photo.camera

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Camera
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.ycr.kernel.log.LogHelper
import com.ycr.module.base.BaseActivity
import com.ycr.module.base.util.ToastHelper
import com.ycr.module.photo.R

/**
 * Created by yuchengren on 2019/1/21.
 */
abstract class CameraBaseActivity: BaseActivity(), SurfaceHolder.Callback {

    var cameraView: SurfaceView? = null
    var finishView: View? = null
    var flashLampView: View? = null
    var takeView: View? = null

    var switchCameraView: View? = null

    var focusView: ImageView? = null
    var focusAnimation: Animation? = null

    var surfaceHolder: SurfaceHolder? = null
    var camera: Camera? = null
    var cameraId: Int = Camera.CameraInfo.CAMERA_FACING_BACK
    var previewRatio: Float = 0f //相机预览宽高比
    var minPreviewWidth: Int = 1000
    var focusAreaWeight = 800

    var isReseting = false
    var scaleGestureDetector: ScaleGestureDetector? = null

    var takeSoundPlayer: MediaPlayer? = null //拍摄的时候播放的声音

    private val lock = Any()

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
    }

    override fun bindView(rootView: View?) {
        super.bindView(rootView)
        initCameraView()
        initFinishView()
        initTakePhotoView()

        initFlashLampView()
        initSwitchCameraView()
        initFocusView()
    }

    private fun initSwitchCameraView() {
        switchCameraView = findViewById(R.id.switchCameraView)
        switchCameraView?.setOnClickListener {
            camera?.apply {
                if(Camera.getNumberOfCameras() >= 2){
                    it.isEnabled = false
                    cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT -  cameraId

                    it.isEnabled = true
                }
            }
        }
    }

    private fun initFocusView() {
        //添加对焦动画视图
        val contentView = findViewById<ViewGroup>(android.R.id.content)?.getChildAt(0) as? ViewGroup
        focusView = ImageView(this).apply {
            setImageResource(R.drawable.ic_camera_focus)
            visibility = View.GONE
        }
        contentView?.addView(focusView,ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT))

        focusAnimation = AnimationUtils.loadAnimation(this,R.anim.focus_animation).apply {
            setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationRepeat(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) { focusView?.visibility = View.GONE }

                override fun onAnimationStart(animation: Animation?) {}
            })
        }

    }

    private fun initFlashLampView() {
        flashLampView = findViewById(R.id.flashLampView)
        flashLampView?.setOnClickListener {
            it.isActivated = !it.isActivated
        }
    }

    private fun initFinishView() {
        finishView = findViewById(R.id.finishView)
        finishView?.setOnClickListener { onBackPressed() }
    }

    private fun initCameraView() {
        cameraView = findViewById(R.id.cameraView)
        cameraView?.run {
            setOnTouchListener { v, event ->
                if(event == null){
                    return@setOnTouchListener false
                }
                if(event.pointerCount == 1){
                     return@setOnTouchListener doCameraFocus(event)
                }else{
                    return@setOnTouchListener doCameraZoom(event)
                }
            }
        }
        initSurfaceHolder()
    }

    private fun doCameraZoom(event: MotionEvent): Boolean {
        if(scaleGestureDetector == null){
            scaleGestureDetector = ScaleGestureDetector(this,object : ScaleGestureDetector.OnScaleGestureListener{
                override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean { return true }
                override fun onScaleEnd(detector: ScaleGestureDetector?) {}
                override fun onScale(detector: ScaleGestureDetector?): Boolean {
                    if(detector == null){
                        return false
                    }
                    camera?.run {
                        parameters = parameters.apply {
                            CameraUtil.handleZoom(detector.scaleFactor > 1 ,this)
                        }
                        return true
                    }
                    return false
                }
            })
        }
        scaleGestureDetector?.onTouchEvent(event)
        return true
    }

    private fun doCameraFocus(event: MotionEvent): Boolean {
        val actionMasked = event.actionMasked
        when(actionMasked){
            MotionEvent.ACTION_DOWN ->{
                camera?.run {
                    focusAnimation?.let {
                        it.cancel()
                        focusView?.run {
                            val marginLayoutParams = layoutParams as? ViewGroup.MarginLayoutParams
                            marginLayoutParams?.setMargins((event.x - width /2f).toInt(),(event.y - height /2f).toInt(),0,0)
                            visibility = View.VISIBLE
                            startAnimation(it)
                        }
                    }

                    parameters = parameters.apply {
                        if(maxNumMeteringAreas <= 0){
                            return@run
                        }
                        cameraView?.let {
                            val focusArea = CameraUtil.caculateFocusArea(it.width, it.height, event.x, event.y)
                            focusAreas = listOf(Camera.Area(focusArea,focusAreaWeight))
                        }
                    }

                    cancelAutoFocus()
                    autoFocus(null)
                }
            }
        }
        return true
    }

    private fun initSurfaceHolder() {
        surfaceHolder = cameraView?.holder?.apply {
            setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
            addCallback(this@CameraBaseActivity)
        }
    }

    private fun initTakePhotoView() {
        takeView = findViewById(R.id.takeView)
        takeView?.setOnClickListener {
            updateCameraFlashLampStatus()
            startTake()
        }
    }

    abstract fun startTake()

    private fun updateCameraFlashLampStatus() {
        camera?.run {
            parameters = parameters.apply {
                flashMode = if(flashLampView?.isActivated == true) Camera.Parameters.FLASH_MODE_ON else Camera.Parameters.FLASH_MODE_OFF
            }
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        initCameraAndStartPreview(holder)
    }

    private fun initCameraAndStartPreview(holder: SurfaceHolder?) {
        if(camera == null){
            if(!initCamera()){
                showInitCameraFailWaring()
                return
            }
        }
        camera?.run {
            setPreviewDisplay(holder)
            startPreview()
        }
    }

    private fun showInitCameraFailWaring() {
        ToastHelper.show("相机初始化失败")
    }

    private fun initCamera(): Boolean {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            return false
        }
        if(camera == null){
            try {
                camera = Camera.open(cameraId).apply {
                    setDisplayOrientation(90 - window.windowManager.defaultDisplay.rotation * 90)
                    parameters = parameters.apply {
                        setRotation(CameraUtil.getRotation(this@CameraBaseActivity,cameraId))
                        val previewSize = getParameterPreviewSize(this)
                        val pictureSize = CameraUtil.getPictureSize(this,previewSize,minPreviewWidth)
                        setPreviewSize(previewSize.width,previewSize.height)
                        setPictureSize(pictureSize.width,pictureSize.height)
                        focusMode = Camera.Parameters.FOCUS_MODE_AUTO
                    }
                }

            }catch (e: Exception){
                LogHelper.e(e)
                return false
            }
        }
        return true
    }

    private fun getParameterPreviewSize(parameters: Camera.Parameters): Camera.Size {
        if(previewRatio == 0f){
            previewRatio = cameraView?.let {
                val height = it.measuredHeight.toFloat()
                if(height == 0f) 0f else it.measuredWidth / height
            }?:0f
        }

        return CameraUtil.getPreviewSize(parameters.supportedPreviewSizes, previewRatio, minPreviewWidth)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        surfaceHolder = holder
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        releaseSurfaceHolder()
    }

    private fun releaseSurfaceHolder(){
        surfaceHolder?.removeCallback(this)
        surfaceHolder = null
    }

    private fun resetView(){
        if(isReseting){
            return
        }
        isReseting = true
        synchronized(lock){
            release()
            initSurfaceHolder()
            initCameraAndStartPreview(surfaceHolder)
            isReseting = false
        }

    }

    private fun releaseCamera(){
        camera?.run {
            stopPreview()
            release()
        }
        camera = null
    }

    private fun release(){
        releaseSurfaceHolder()
        releaseCamera()
    }

    override fun onRestart() {
        super.onRestart()
        resetView()
    }

    override fun onDestroy() {
        release()
        takeSoundPlayer?.release()
        super.onDestroy()
    }


}