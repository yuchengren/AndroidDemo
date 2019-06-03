package com.ycr.kernel.union.task

import com.ycr.kernel.task.*
import com.ycr.kernel.util.getTaskNameFromTrace

/**
 * created by yuchengren on 2019/4/23
 */
object TaskHelper {
    @JvmStatic fun <R> buildTask(groupName: String?, taskName: String?, doBackground: ITaskBackground<R>, callback: ITaskCallback<R>): AsyncTaskInstance<R> {
        val task = AsyncTaskInstance(doBackground, callback)
        task.setGroupName(groupName)
        task.setTaskName(taskName)
        return task
    }

    @JvmStatic fun <R> submitTask(groupName: String?, taskName: String?, doBackground: ITaskBackground<R>, callback: ITaskCallback<R>): AsyncTaskInstance<R> {
        val task = buildTask(groupName, taskName, doBackground, callback)
        task.setSerial(false)
        task.setPolicy(ITaskPolicy.DISCARD_NEW)
        TaskScheduler.submit(task)
        return task
    }

    @JvmStatic fun <R> submitTask(groupName: String?, taskName: String?, taskCallBack: CommonTask<R>): AsyncTaskInstance<R> {
        return submitTask(groupName,taskName,taskCallBack,taskCallBack)
    }

    @JvmStatic fun <R> submitTask(taskName: String?,taskCallBack: CommonTask<R>): AsyncTaskInstance<R> {
        return submitTask(IGroup.GROUP_NAME_DEFAULT,taskName,taskCallBack)
    }

    @JvmStatic fun <R> submitTask(taskCallBack: CommonTask<R>): AsyncTaskInstance<R> {
        return submitTask(IGroup.GROUP_NAME_DEFAULT,getDefaultTaskName(),taskCallBack)
    }

    @JvmStatic fun cancelGroup(groupName: String?) {
        TaskScheduler.cancelGroup(groupName)
    }

    @JvmStatic fun cancelTask(groupName: String?, taskName: String?) {
        TaskScheduler.cancelTask(groupName, taskName)
    }

    @JvmStatic fun cancelTask(taskName: String?) {
        TaskScheduler.cancelTask(IGroup.GROUP_NAME_DEFAULT, taskName)
    }

    @JvmStatic fun cancelTask(task: AbstractTask<*>) {
        TaskScheduler.cancelTask(task)
    }

    /**
     * 默认TaskName为当前调用submitTask为，所在类名称:代码行数 如com.mockuai.mkselleros.module.commodity.distribution.CommodityListDistributionFragment:200
     * 若重复提交相同taskName任务，则目前策略为丢弃新任务,老任务继续执行
     * 若是foreach里循环执行submitTask，则需要指定不同的taskName，否则第二个及之后的任务可能不会被执行
     */
    @JvmStatic fun getDefaultTaskName(): String{
        return Thread.currentThread().getTaskNameFromTrace(5)
    }
}
