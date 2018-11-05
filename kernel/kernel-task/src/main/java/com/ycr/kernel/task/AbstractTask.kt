package com.ycr.kernel.task

import com.ycr.kernel.task.util.runOnMainThread
import com.ycr.kernel.task.util.taskLog
import java.util.concurrent.Callable
import java.util.concurrent.FutureTask

/**
 * Created by yuchengren on 2018/8/6.
 */
class AbstractTask<R>(doBackground: ITaskBackground<R>,private val callback: ITaskCallback<R>?):
        FutureTask<R>(Callable<R> {  doBackground.doInBackground() }) ,ITaskCallback<R>, IGroup,ITask,ITaskStatus,ITaskPolicy,ITaskPriority{
    private var groupName: String? = IGroup.GROUP_NAME_DEFAULT
    private var taskName: String? = ITask.TASK_NAME_DEFAULT
    private var serial: Boolean = false
    private var status: Int = ITaskStatus.NEW
    private var policy = ITaskPolicy.DISCAR_NEW
    private var priority: Int = ITaskPriority.PRIORITY_NORMAL

    private var beginExecuteTime: Long = 0
    private var endExecuteTime: Long = 0
    override fun run() {
        beginExecuteTime = System.currentTimeMillis()
        super.run()
    }

    override fun done() {
        endExecuteTime = System.currentTimeMillis()
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
        Runnable { callback?.onBeforeCall() }.runOnMainThread()
    }

    override fun onAfterCall() {
        Runnable { callback?.onAfterCall() }.runOnMainThread()
    }

    override fun onCompleted(data: R) {
        Runnable { callback?.onCompleted(data) }.runOnMainThread()
    }

    override fun onCancelled() {
        Runnable { callback?.onCancelled() }.runOnMainThread()
    }

    override fun onError(t: Throwable) {
        Runnable { callback?.onError(t) }.runOnMainThread()
    }

    override fun groupName(): String? {
        return groupName
    }

    override fun getTaskName(): String? {
        return taskName
    }

    override fun isSerial(): Boolean {
        return serial
    }

    override fun retry() {
        TaskSchduler.submit(this)
    }

    override fun setStatus(status: Int) {
        this.status = status
    }

    override fun getStatus(): Int {
        return status
    }

    override fun getPolicy(): Int {
        return policy
    }

    override fun getPriority(): Int {
        return priority
    }



}