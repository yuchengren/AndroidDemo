package com.ycr.module.framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.ycr.kernel.http.IResult;
import com.ycr.kernel.task.AsyncTaskInstance;
import com.ycr.kernel.task.IGroup;
import com.ycr.kernel.union.mvp.UnionActivity;
import com.ycr.kernel.union.task.TaskHelper;
import com.ycr.kernel.util.ThreadUtilsKt;
import com.ycr.module.framework.helper.InjectHelper;
import com.ycr.module.framework.task.ApiTask;
import com.ycr.module.framework.view.ILayoutInflaterFactoryCreator;

import butterknife.ButterKnife;

/**
 * Created by yuchengren on 2018/12/10.
 */
public abstract class SuperActivity extends UnionActivity {

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

	protected boolean isSupportButterKnife(){
		return true;
	}

	protected boolean isSupportDagger(){
		return false;
	}

	@Override
	protected void beforeCreate(@Nullable Bundle savedInstanceState) {
		super.beforeCreate(savedInstanceState);
		doInject();
	}

	protected void doInject() {
		if(isSupportDagger()){

		}
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		ILayoutInflaterFactoryCreator layoutInflaterFactoryCreator = InjectHelper.layoutInflaterFactoryCreator;
		if (layoutInflaterFactoryCreator != null) {
			LayoutInflater.Factory2 factory2 = layoutInflaterFactoryCreator
					.createLayoutInflaterFactory2(getDelegate());
			if (factory2 != null) {
				LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), factory2);
			}
		}
		super.onCreate(savedInstanceState);
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
