package com.ycr.kernel.task

import com.ycr.kernel.task.util.runOnMainThread
import com.ycr.kernel.task.util.taskLog
import java.util.concurrent.Callable
import java.util.concurrent.FutureTask

/**
 * Created by yuchengren on 2018/8/6.
 */
abstract class AbstractTask<R>(doBackground: ITaskBackground<R>,val callback: ITaskCallback<R>):
        FutureTask<R>(Callable<R> {  doBackground.doInBackground() }) ,ITaskCallback<R>{

    override fun run() {
        super.run()
    }

    override fun done() {
        super.done()
        onAfterCall()
        if(isCancelled){
            onCancelled()
        }else{
            val result = get()
            if(result == null){
                taskLog.d("")
            }else{
                onCompleted(get())
            }
        }
    }

    override fun setException(t: Throwable?) {
        super.setException(t)
        t?.apply { onError(this) }
    }

    override fun onBeforeCall() {
        Runnable { callback.onBeforeCall() }.runOnMainThread()
    }

    override fun onAfterCall() {
        Runnable { callback.onAfterCall() }.runOnMainThread()
    }

    override fun onCompleted(data: R) {
        Runnable { callback.onAfterCall() }.runOnMainThread()
    }

    override fun onCancelled() {
        Runnable { callback.onAfterCall() }.runOnMainThread()
    }

    override fun onError(t: Throwable) {
        Runnable { callback.onAfterCall() }.runOnMainThread()
    }



}