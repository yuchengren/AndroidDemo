package com.ycr.module.framework.task

import com.ycr.kernel.http.IResult
import com.ycr.kernel.task.ITaskBackground
import com.ycr.kernel.util.runOnMainThread

/**
 * Created by yuchengren on 2018/12/10.
 */
abstract class ApiTask<T>: ApiCallback<T>() ,ITaskBackground<IResult<T>>{

    fun <P> postProgress(progress: P){
        Runnable {
            try {
                onProgress(progress)
            }catch (tr: Throwable){
                printException("postProgress",tr)
            }
        }.runOnMainThread()

    }

    open fun <P> onProgress(progress: P){

    }
}