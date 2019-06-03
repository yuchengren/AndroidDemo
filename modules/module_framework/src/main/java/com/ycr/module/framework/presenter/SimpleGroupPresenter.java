package com.ycr.module.framework.presenter;

import com.ycr.kernel.http.IResult;
import com.ycr.kernel.task.AsyncTaskInstance;
import com.ycr.kernel.task.IGroup;
import com.ycr.kernel.union.mvp.presenter.GroupPresenter;
import com.ycr.kernel.union.task.TaskHelper;
import com.ycr.module.framework.task.ApiTask;

/**
 * Created by yuchengren on 2018/12/10.
 */
public class SimpleGroupPresenter extends GroupPresenter {

	public SimpleGroupPresenter(){
		super(IGroup.Companion.getDefaultGroup());
	}

	public <T> AsyncTaskInstance<IResult<T>> submitTask(ApiTask<T> task){
		return TaskHelper.INSTANCE.submitTask(groupName(),getDefaultTaskName(),task,task);
	}
}
