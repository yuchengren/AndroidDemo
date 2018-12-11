package com.yuchengren.demo.view

import android.content.Context
import android.support.annotation.Nullable
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View

/**
 * Created by yuchengren on 2018/11/15.
 */
class TestView : View {

    private lateinit var gestureDetector: GestureDetector
    private lateinit var scaleGestureDetector: ScaleGestureDetector

    constructor(context: Context) : this(context, null)

    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initGesture(context)
    }

    private fun initGesture(context: Context) {
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent?): Boolean {
                return true
            }

            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                Log.e("onScroll", "${distanceX},${distanceY}")
                return true
            }
        })

        scaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.OnScaleGestureListener {
            override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
                return true
            }

            override fun onScaleEnd(detector: ScaleGestureDetector?) {}
            /**
             * focusX focusY 返回组成缩放手势(两个手指)中点x横纵坐标
             * scaleFactor 获取本次缩放事件的缩放因子
             * return 返回值表示是否下次缩放需要重置，如果返回ture，那么detector就会重置缩放事件，如果返回false，detector会在之前的缩放上继续进行计算
             */
            override fun onScale(detector: ScaleGestureDetector?): Boolean {
                if (detector == null) {
                    return false
                }
                Log.e("onScale", "${detector.scaleFactor},${detector.focusX},${detector.focusY}}")
                return true
            }
        })
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return super.onTouchEvent(event)
        }

        when(event.actionMasked){
            MotionEvent.ACTION_DOWN ->  Log.e("ACTION_DOWN", "${event?.actionIndex}")
            MotionEvent.ACTION_MOVE ->  Log.e("ACTION_MOVE", "${event?.actionIndex}")
            MotionEvent.ACTION_UP ->  Log.e("ACTION_UP", "${event?.actionIndex}")
            MotionEvent.ACTION_POINTER_DOWN ->  Log.e("ACTION_POINTER_DOWN", "${event?.actionIndex}")
            MotionEvent.ACTION_POINTER_UP ->  Log.e("ACTION_POINTER_UP", "${event?.actionIndex}")
            MotionEvent.ACTION_CANCEL ->  Log.e("ACTION_CANCEL", "${event?.actionIndex}")
            MotionEvent.ACTION_OUTSIDE ->  Log.e("ACTION_OUTSIDE", "${event?.actionIndex}")
        }

        scaleGestureDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)

        return true
    }
}