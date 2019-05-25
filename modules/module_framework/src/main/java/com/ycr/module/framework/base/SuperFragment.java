package com.ycr.module.framework.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.ycr.kernel.http.IResult;
import com.ycr.kernel.task.AsyncTaskInstance;
import com.ycr.kernel.task.IGroup;
import com.ycr.kernel.union.mvp.UnionFragment;
import com.ycr.kernel.union.task.TaskHelper;
import com.ycr.kernel.util.ThreadUtilsKt;
import com.ycr.module.framework.task.ApiTask;

import butterknife.ButterKnife;

/**
 * Created by yuchengren on 2018/12/10.
 */
public abstract class SuperFragment extends UnionFragment {

	@Override
	public void beforeBindView() {

	}

	@Override
	public void bindView(View rootView) {
		if(isSupportButterKnife()){
			ButterKnife.bind(this,rootView);
		}
	}

	@Override
	public void afterBindView(View rootView, Bundle savedInstanceState) {

	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		doInject();
	}

	private void doInject() {

	}

	protected boolean isSupportButterKnife(){
		return true;
	}

	public String getDefaultTaskName(){
		return ThreadUtilsKt.getTaskNameFromTrace(Thread.currentThread(),5);
	}

	public <T> AsyncTaskInstance<IResult<T>> submitTask(ApiTask<T> task){
		return TaskHelper.submitTask(groupName(),getDefaultTaskName(),task,task);
	}

	public <T> AsyncTaskInstance<IResult<T>> submitTask(String groupName, String taskName, ApiTask<T> task){
		return TaskHelper.submitTask(groupName,taskName,task,task);
	}

	public <T> AsyncTaskInstance<IResult<T>> submitTask(String taskName, ApiTask<T> task){
		return TaskHelper.submitTask(groupName(),taskName,task,task);
	}

	public <T> AsyncTaskInstance<IResult<T>> submitTaskDefaultGroup(ApiTask<T> task) {
		return TaskHelper.submitTask(IGroup.GROUP_NAME_DEFAULT, getDefaultTaskName(), task, task);
	}
}
