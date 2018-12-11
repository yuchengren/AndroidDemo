package com.ycr.module.framework.presenter;

import com.ycr.kernel.http.IResult;
import com.ycr.kernel.mvp.view.IMvpView;
import com.ycr.kernel.task.AsyncTask;
import com.ycr.kernel.task.IGroup;
import com.ycr.kernel.union.helper.ContextHelper;
import com.ycr.kernel.union.mvp.presenter.BaseLifeCyclePresenter;
import com.ycr.kernel.union.task.TaskHelper;
import com.ycr.module.framework.task.ApiTask;

/**
 * Created by yuchengren on 2018/12/7.
 */
public abstract class BasePresenter<V extends IMvpView> extends BaseLifeCyclePresenter<V> {

	public BasePresenter(V mvpView) {
		super(mvpView);
	}

	public <T> AsyncTask<IResult<T>> submitTask(ApiTask<T> task){
		return TaskHelper.INSTANCE.submitTask(groupName(),getDefaultTaskName(),task,task);
	}

	public <T> AsyncTask<IResult<T>> submitTask(String groupName,String taskName,ApiTask<T> task){
		return TaskHelper.INSTANCE.submitTask(groupName,taskName,task,task);
	}

	public <T> AsyncTask<IResult<T>> submitTaskDefaultGroup(ApiTask<T> task){
		return TaskHelper.INSTANCE.submitTask(IGroup.GROUP_NAME_DEFAULT,getDefaultTaskName(),task,task);
	}

	public String getString(int resId){
		return ContextHelper.INSTANCE.getString(resId);
	}

	public String getString(int resId,Object... args){
		return ContextHelper.INSTANCE.getString(resId,args);
	}
}
