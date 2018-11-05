package com.ycr.kernel.task

import android.os.Process

/**
 * Created by yuchengren on 2018/9/13.
 */
class PriorityThread(private val pri: Int, name: String,r: Runnable) : Thread(r, name) {
    override fun run() {
        if(pri != NORM_PRIORITY){
            Process.setThreadPriority(pri)
        }
        super.run()
    }
}