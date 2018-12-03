package com.ycr.kernel.task

import android.os.*
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.TimeUnit

/**
 * Created by yuchengren on 2018/9/13.
 */
object TaskScheduler {
    private const val MSG_TYPE_TASK_SUBMIT = 0 //消息类型 任务提交
    private const val MSG_TYPE_TASK_OVER = 1 //消息类型 任务结束
    private const val MSG_TYPE_TASK_PRIORITY_CHANGE = 2 //消息类型 任务优先级变更

    private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
    private val CORE_POOL_SIZE = CPU_COUNT * 2 + 1 // 核心线程数
    private val MAXIMUM_POOL_SIZE = CPU_COUNT * 16 + 1 //最大线程数
    private val QUEUE_SIZE = CORE_POOL_SIZE + 1 //线程队列大小
    private const val KEEP_ALIVE_TIME = 1L //线程存活时间
    private val executor: TaskThreadPoolExecutor
    private val handler: Handler
    private val taskPriorityManager: TaskPriorityManager
    init {
        val queue:PriorityBlockingQueue<Runnable> = PriorityBlockingQueue(QUEUE_SIZE)
        val threadFactory = PriorityThreadFactory(
                "priority-thread-pool-",Process.THREAD_PRIORITY_BACKGROUND)
        executor = TaskThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS
                ,queue,threadFactory,ExecutorInterceptor())
        taskPriorityManager = TaskPriorityManager(this,queue)

        val taskThread = HandlerThread("task-handler-thread")
        taskThread.start()
        handler = Handler(taskThread.looper,HandlerCallback())

    }

    private class ExecutorInterceptor: TaskThreadPoolExecutor.Interceptor{

        override fun beforeExecute(t: Thread?, r: Runnable?) {
            (r as? AbstractTask<*>)?.setStatus(ITaskStatus.RUNNING)

        }
        override fun afterExecute(r: Runnable?, t: Throwable?) {
            (r as? AbstractTask<*>)?.setStatus(ITaskStatus.OVER)
            handler.sendMessage(handler.obtainMessage(MSG_TYPE_TASK_OVER,r))
        }

    }

    private class HandlerCallback: Handler.Callback{

        override fun handleMessage(msg: Message?): Boolean {
            val what = msg?.what?:return true
            when(what){
                MSG_TYPE_TASK_SUBMIT ->{
                    val task = msg.obj as AbstractTask<*>
                    doSubmitTask(task)
                }
                MSG_TYPE_TASK_OVER -> {
                    val task = msg.obj as AbstractTask<*>
                    onTaskOver(task)
                }
                MSG_TYPE_TASK_PRIORITY_CHANGE -> {
                    val uiStatus = msg.arg1
                    val taskNames = msg.obj as? Array<String>
                    var groupName: String? = null
                    var taskName: String? = null
                    if(taskNames != null){
                        when(taskNames.size){
                            1 -> groupName = taskNames[0]
                            2 ->{
                                groupName = taskNames[0]
                                taskName = taskNames[1]
                            }
                        }
                    }
                    if(groupName != null){
                        taskPriorityManager.changeTaskPriority(uiStatus,groupName,taskName)
                    }
                }
            }
            return true
        }
    }

    private fun onTaskOver(task: AbstractTask<*>) {
        removeTask(task) // 从分组中移除执行完毕的任务
        submitWaitTask() //提交下一个等待状态的任务
    }

    private fun submitWaitTask() {
        val waitCanSubmitTask = taskPriorityManager.getWaitCanSubmitTask()?:return
        taskPriorityManager.onWaitTaskSubmit(waitCanSubmitTask)
        submitReadyTask(waitCanSubmitTask)
    }

    fun <R> submit(task: AbstractTask<R>){
        handler.sendMessage(handler.obtainMessage(MSG_TYPE_TASK_SUBMIT,task))
    }

    private fun doSubmitTask(task: AbstractTask<*>) {
        task.onSubmit()
        if(task.groupName() == IGroup.GROUP_NAME_DEFAULT){
            submitReadyTask(task)
            task.onBeforeCall()
            return
        }
        val oldTask = taskPriorityManager.getOldTask(task)
        if(oldTask != null){
            //任务去重策略
            val policy = task.policy()
            when (policy){
                ITaskPolicy.DISCARD_NEW ->{
                    TaskLog.w("discard new task,${oldTask.groupName()}-${oldTask.taskName()}")
                    return
                }
                ITaskPolicy.CANCEL_OLD ->{
                    stopTask(oldTask)
                    TaskLog.w("cancel old task,${oldTask.groupName()}-${oldTask.taskName()}")
                }
                ITaskPolicy.FORCE_SUBMIT ->{
                    task.setTaskName(task.taskName()+ "-" + System.currentTimeMillis())
                    TaskLog.w("force submit task,${oldTask.groupName()}-${oldTask.taskName()}")
                }
            }
        }
        //检测任务是否可以提交，不可提交的设置任务为等待状态
        if(isCanSubmit(task)){
            //添加任务到分组
            taskPriorityManager.addTask(task)
            submitReadyTask(task)
        }else{
            task.setStatus(ITaskStatus.WAIT)
            taskPriorityManager.addWaitTask(task)
        }
        task.onBeforeCall()
    }

    internal fun isCanSubmit(task: AbstractTask<*>): Boolean {
        //如果线程池队列已满，且线程池达到最大线程数，则禁止任务提交,否则会抛出java.util.concurrent.RejectedExecutionException
        if(executor.queue.size == QUEUE_SIZE && executor.activeCount == MAXIMUM_POOL_SIZE){
            TaskLog.w("task ${task.groupName()}-${task.taskName()} can not submit,executor queue and pool is full")
            return false
        }
        if(task.isSerial() && taskPriorityManager.isGroupHasAliveTask(task.groupName())){
            TaskLog.i("task ${task.groupName()}-${task.taskName()}is serial and group has alive task,need wait,")
            return false
        }
        return true
    }

    private fun stopTask(task: AbstractTask<*>,isNeedSubmitNextWaitTask: Boolean = false) {
        if(!task.isCancelled){
            task.cancel(true) //手动取消任务 也会触发一次Done()
            task.setStatus(ITaskStatus.CANCEL)
            TaskLog.w("task is cancelled,${task.groupName()}-${task.taskName()}")
        }
        if(isNeedSubmitNextWaitTask){
            handler.sendMessage(handler.obtainMessage(MSG_TYPE_TASK_OVER,task))
        }else{
            removeTask(task)
        }
    }

    private fun removeTask(task: AbstractTask<*>){
        taskPriorityManager.removeTask(task)
    }

    fun submitReadyTask(task: AbstractTask<*>) {
        task.setStatus(ITaskStatus.READY)
        executor.submit(task)
        TaskLog.d("submit ready task ,${task.groupName()}-${task.taskName()}")
    }

    fun cancelTask(task: AbstractTask<*>){
        when(task.status()){
            ITaskStatus.WAIT -> taskPriorityManager.removeWaitTask(task)
            else ->{
                stopTask(task,true)
            }
        }
    }

    private fun scheduleTask(uiStatus: Int,groupName: String?, taskName: String?){
        handler.sendMessage(handler.obtainMessage(MSG_TYPE_TASK_PRIORITY_CHANGE,uiStatus, 0,arrayOf(groupName,taskName)))
    }

    fun onPause(groupName: String?){
        scheduleTask(UIStatus.STOP,groupName,null)
    }

    fun onResume(groupName: String?){
        scheduleTask(UIStatus.START,groupName,null)
    }

    fun cancelGroup(groupName: String?){
        scheduleTask(UIStatus.DESTROY,groupName,null)
    }

    fun cancelTask(groupName: String?, taskName: String?){
        scheduleTask(UIStatus.DESTROY,groupName,taskName)
    }
}