package com.ycr.kernel.union.mvp.presenter;

import com.ycr.kernel.http.IResult;
import com.ycr.kernel.task.AsyncTaskInstance;
import com.ycr.kernel.task.IGroup;
import com.ycr.kernel.task.ITaskBackground;
import com.ycr.kernel.union.task.ResultCallback;
import com.ycr.kernel.union.task.TaskHelper;
import com.ycr.kernel.util.ThreadUtilsKt;

import org.jetbrains.annotations.Nullable;

/**
 * Created by yuchengren on 2018/12/7.
 */
public class GroupPresenter implements IGroup {
	private IGroup group;

	public GroupPresenter(){}

	public GroupPresenter(IGroup group){
		this.group = group;
	}

	@Nullable
	@Override
	public String groupName() {
		return group == null? getClass().getName(): group.groupName();
	}

	public String getDefaultTaskName(){
		return ThreadUtilsKt.getTaskNameFromTrace(Thread.currentThread(),5);
	}

	public <T> AsyncTaskInstance<IResult<T>> submitTask(String taskName, ITaskBackground<IResult<T>> taskBackground, ResultCallback<T> resultCallback){
		return TaskHelper.submitTask(groupName(),taskName,taskBackground,resultCallback);
	}

	public <T> AsyncTaskInstance<IResult<T>> submitTask(ITaskBackground<IResult<T>> taskBackground, ResultCallback<T> resultCallback){
		return submitTask(getDefaultTaskName(),taskBackground,resultCallback);
	}
}
