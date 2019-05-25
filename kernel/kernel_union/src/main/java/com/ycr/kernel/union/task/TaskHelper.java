package com.ycr.kernel.union.task;

import com.ycr.kernel.task.AsyncTaskInstance;
import com.ycr.kernel.task.ITaskBackground;
import com.ycr.kernel.task.ITaskCallback;
import com.ycr.kernel.task.ITaskPolicy;
import com.ycr.kernel.task.TaskScheduler;

/**
 * created by yuchengren on 2019/4/23
 */
public class TaskHelper {
    public static <R> AsyncTaskInstance<R> buildTask(String groupName, String taskName, ITaskBackground<R> doBackground, ITaskCallback<R> callback){
        AsyncTaskInstance<R> task = new AsyncTaskInstance(doBackground,callback);
        task.setGroupName(groupName);
        task.setTaskName(taskName);
        return task;
    }

    public static <R> AsyncTaskInstance<R> submitTask(String groupName, String taskName, ITaskBackground<R> doBackground, ITaskCallback<R> callback){
        AsyncTaskInstance<R> task = buildTask(groupName, taskName, doBackground, callback);
        task.setSerial(false);
        task.setPolicy(ITaskPolicy.DISCARD_NEW);
        TaskScheduler.INSTANCE.submit(task);
        return task;
    }
}
