package com.ycr.kernel.union.task;

import com.ycr.kernel.task.AsyncTask;
import com.ycr.kernel.task.ITaskBackground;
import com.ycr.kernel.task.ITaskCallback;
import com.ycr.kernel.task.ITaskPolicy;
import com.ycr.kernel.task.TaskScheduler;
import com.ycr.kernel.union.R;

/**
 * created by yuchengren on 2019/4/23
 */
public class TaskHelper {
    public static <R> AsyncTask<R> buildTask(String groupName, String taskName, ITaskBackground<R> doBackground, ITaskCallback<R> callback){
        AsyncTask<R> task = new AsyncTask(doBackground,callback);
        task.setGroupName(groupName);
        task.setTaskName(taskName);
        return task;
    }

    public static <R> AsyncTask<R> submitTask(String groupName, String taskName, ITaskBackground<R> doBackground, ITaskCallback<R> callback){
        AsyncTask<R> task = buildTask(groupName, taskName, doBackground, callback);
        task.setSerial(false);
        task.setPolicy(ITaskPolicy.DISCARD_NEW);
        TaskScheduler.INSTANCE.submit(task);
        return task;
    }
}
