package com.ycr.kernel.task.util

import android.app.ActivityManager
import android.content.Context
import android.os.Looper
import android.os.Process
import android.text.TextUtils
import com.ycr.kernel.log.LogHelper

/**
 * Created by yuchengren on 2018/8/6.
 */

/**
 * 主线程Handler
 */
internal val mainHandler: android.os.Handler = android.os.Handler(Looper.getMainLooper())

/**
 * 是否当前进程是主进程
 */
fun Context.isMainProcess(): Boolean{
    return TextUtils.equals(getProcessName(),packageName)
}

/**
 * 获取当前进程名
 */
fun Context.getProcessName(): String?{
    val myPid = Process.myPid()
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val runningAppProcesses = activityManager?.runningAppProcesses
    runningAppProcesses?.forEach {
        if(it.pid == myPid){
            return it.processName
        }
    }
    return null
}

/**
 * 是否是当前线程是主线程
 */
fun isMainThread(): Boolean{
    return Looper.myLooper() == Looper.getMainLooper()
}

/**
 * 切换到主线程中执行任务
 */
fun Runnable.runOnMainThread(){
    mainHandler.post(this)
}

/**
 * 切换到主线程中执行延迟任务
 */
fun Runnable.runOnMainThreadDelay(delayMillis: Long){
    mainHandler.postDelayed(this,delayMillis)
}

/**
 * 取消主线程中的该任务
 */
fun Runnable.removeCallbacksOnMainThread(){
    mainHandler.removeCallbacks(this)
}












