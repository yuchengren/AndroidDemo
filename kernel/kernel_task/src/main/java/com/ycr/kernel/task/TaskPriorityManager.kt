package com.ycr.kernel.task

import java.util.concurrent.BlockingQueue
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by yuchengren on 2018/11/29.
 */
class TaskPriorityManager(private var taskScheduler: TaskScheduler, private var poolExecutorQueue: BlockingQueue<Runnable>) {

    private val taskGroups = ConcurrentHashMap<String?,MutableList<AbstractTask<*>>>()
    private val waitTasks = mutableListOf<AbstractTask<*>>()

    fun getOldTask(task: AbstractTask<*>): AbstractTask<*>? {
        val taskList = taskGroups[task.groupName()]
        if(taskList == null || taskList.isEmpty()){
            return null
        }
        for (oldTask in taskList) {
            if(task == oldTask){
                return oldTask
            }
        }
        return null
    }

    fun removeTask(task: AbstractTask<*>) {
        val group = taskGroups[task.groupName()]
        if(group == null || group.isEmpty()){
            return
        }
        group.remove(task)
        if(group.isEmpty()){
            taskGroups.remove(task.groupName())
        }
    }

    fun addTask(task: AbstractTask<*>) {
        getGroup(task.groupName()).add(task)
    }

    fun addWaitTask(task: AbstractTask<*>) {
        addTask(task)
        waitTasks.add(task)
    }

    private fun getGroup(groupName: String?): MutableList<AbstractTask<*>>{
        var group = taskGroups[groupName]
        if(group == null){
            group = CopyOnWriteArrayList<AbstractTask<*>>()
            taskGroups[groupName] = group
        }
        return group
    }

    fun isGroupHasAliveTask(groupName: String?): Boolean{
        val group = taskGroups[groupName]
        if(group == null || group.isEmpty()){
            return false
        }
        group.forEach {
            when(it.status()){
                ITaskStatus.READY,ITaskStatus.RUNNING -> return true
            }
        }
        return false
    }

    fun getWaitCanSubmitTask(): AbstractTask<*>? {
        if(waitTasks.isEmpty()){
            return null
        }
        waitTasks.forEach {
            if(taskScheduler.isCanSubmit(it)){
                return it
            }
        }
        return null
    }

    fun removeWaitTask(task: AbstractTask<*>){
        removeTask(task)
        waitTasks.remove(task)
    }

    fun onWaitTaskSubmit(task: AbstractTask<*>){
        waitTasks.remove(task)
    }

    fun changeTaskPriority(uiStatus: Int, groupName: String, taskName: String?) {
        val group = taskGroups[groupName]
        if(group == null || group.isEmpty()){
            return
        }
        when(uiStatus){
            UIStatus.STOP ->{
                for (task in group){
                    if(taskName != null && taskName != task.taskName()){
                        continue
                    }
                    changePriority(task,task.priority() - 1)
                }
            }
            UIStatus.START ->{
                for (task in group){
                    if(taskName != null && taskName != task.taskName()){
                        continue
                    }
                    when(task.priority()){
                        ITaskPriority.PRIORITY_NORMAL,ITaskPriority.PRIORITY_UI,ITaskPriority.PRIORITY_HIGH -> return
                        else ->  changePriority(task,task.priority() + 1)
                    }

                }
            }
            UIStatus.DESTROY ->{
                for (task in group){
                    if(taskName != null && taskName != task.taskName()){
                        continue
                    }
                    taskScheduler.cancelTask(task)
                }
            }
        }

    }

    private fun changePriority(task: AbstractTask<*>, priority: Int) {
        if(poolExecutorQueue.isEmpty()){
            return
        }
        if(task.status() != ITaskStatus.READY){
            return
        }
        if(poolExecutorQueue.remove(task)){
            task.setPriority(priority)
            taskScheduler.submitReadyTask(task)
        }
    }
}