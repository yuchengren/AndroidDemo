package com.ycr.kernel.task

import com.ycr.kernel.util.isMainThread
import com.ycr.kernel.util.runOnMainThread
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.FutureTask

/**
 * Created by yuchengren on 2018/8/6.
 */
open class AbstractTask<R>(doBackground: ITaskBackground<R>, private val callback: ITaskCallback<R>?) :
        FutureTask<R>(Callable<R> { doBackground.doInBackground() }), IGroup, ITask, ITaskStatus, ITaskPolicy, ITaskPriority {

    private var groupName: String? = IGroup.GROUP_NAME_DEFAULT
    private var taskName: String? = ITask.TASK_NAME_DEFAULT
    private var isSerial: Boolean = false
    private var status: Int = ITaskStatus.NEW
    private var policy = ITaskPolicy.DISCARD_NEW
    private var priority: Int = ITaskPriority.PRIORITY_NORMAL

    private var submitTime: Long = 0
    private var beginExecuteTime: Long = 0
    private var endExecuteTime: Long = 0
    override fun run() {
        beginExecuteTime = System.currentTimeMillis()
        super.run()
    }

    override fun done() {
        endExecuteTime = System.currentTimeMillis()
        Runnable {
            onAfterCall()
            if (isCancelled) {
                onCancelled()
            } else {
                onCompleted()
            }
        }.runOnMainThread()

        val waitTime = beginExecuteTime - submitTime
        val runTime = endExecuteTime - beginExecuteTime
        val totalTime = endExecuteTime - beginExecuteTime
        TaskLog.d("task-$groupName-$taskName done,waitTime = $waitTime,runTime = $runTime, totalTime = $totalTime")
    }

    override fun setException(tr: Throwable?) {
        super.setException(tr)
        endExecuteTime = System.currentTimeMillis()
        printException(tr, "setException")
        if (isMainThread()) onAfterCall() else Runnable { onAfterCall() }.runOnMainThread()
        if (tr == null) {
            return
        }
        if (isMainThread()) onError(tr) else Runnable { onError(tr) }.runOnMainThread()
    }

    fun onBeforeCall() {
        if (isMainThread()) onBeforeCallMain() else Runnable { onBeforeCallMain() }.runOnMainThread()
    }

    private fun onBeforeCallMain() {
        try {
            callback?.onBeforeCall(this)
        } catch (tr: Throwable) {
            printException(tr, "onBeforeCall")
        }
    }

    private fun printException(tr: Throwable?, period: String) {
        val cause: Throwable? = if (tr is ExecutionException) {
            tr.cause
        } else tr
        TaskLog.e(cause, "task-$groupName-$taskName occur exception at $period ")
    }

    private fun onAfterCall() {
        try {
            callback?.onAfterCall()
        } catch (tr: Throwable) {
            printException(tr, "onAfterCall")
        }
    }

    private fun onCompleted() {
        try {
            val result: R? = get()
            if (result == null) {
                TaskLog.d("task-$groupName-$taskName get() result is null")
            } else {
                callback?.onCompleted(result)
            }
        } catch (tr: Throwable) {
            printException(tr, "onCompleted")
        }
    }

    private fun onCancelled() {
        try {
            callback?.onCancelled()
            TaskLog.d("task-$groupName-$taskName is cancelled")
        } catch (tr: Throwable) {
            printException(tr, "onCancelled")
        }

    }

    private fun onError(t: Throwable) {
        try {
            callback?.onError(t)
        } catch (tr: Throwable) {
            printException(tr, "onError")
        }
    }

    override fun groupName(): String? {
        return groupName
    }

    fun setGroupName(groupName: String?) {
        this.groupName = groupName
    }

    override fun taskName(): String? {
        return taskName
    }

    override fun setTaskName(taskName: String?) {
        this.taskName = taskName
    }

    override fun isSerial(): Boolean {
        return isSerial
    }

    fun setSerial(isSerial: Boolean) {
        this.isSerial = isSerial
    }

    override fun retry() {
        TaskScheduler.submit(this)
    }

    fun setStatus(status: Int) {
        this.status = status
    }

    override fun status(): Int {
        return status
    }

    override fun policy(): Int {
        return policy
    }

    fun setPolicy(policy: Int) {
        this.policy = policy
    }

    override fun priority(): Int {
        return priority
    }

    fun setPriority(priority: Int) {
        this.priority = priority
    }

    fun onSubmit() {
        submitTime = System.currentTimeMillis()
    }

    override fun hashCode(): Int {
        return (groupName?.hashCode() ?: 0) * 31 + (taskName?.hashCode() ?: 0)
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (this === other) {
            return true
        }
        if (other::class != this::class) {
            return false
        }
        other as AbstractTask<*>
        return this.groupName == other.groupName && this.taskName == other.taskName
    }

    /**
     * 任务队列优先级从大到小排列，优先级越高，越先执行
     */
    override fun compareTo(other: ITaskPriority): Int {
        return other.priority() - this.priority
    }

}