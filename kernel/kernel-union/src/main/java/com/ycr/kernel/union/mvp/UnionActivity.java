package com.ycr.kernel.union.mvp;

import android.os.Bundle;

import com.ycr.kernel.mvp.MvpActivity;
import com.ycr.kernel.task.IGroup;
import com.ycr.kernel.task.TaskScheduler;
import com.ycr.kernel.union.UnionModuleKt;
import com.ycr.kernel.union.http.HttpHelper;
import com.ycr.kernel.union.mvp.view.IDefineView;
import com.ycr.kernel.union.mvp.view.ViewCreateHelper;
import com.ycr.kernel.util.KeyBoardUtils;


/**
 * Created by yuchengren on 2018/12/10.
 */
public abstract class UnionActivity extends MvpActivity implements IGroup,IDefineView{

	@Override
	protected void afterCreate(Bundle savedInstanceState) {
		super.afterCreate(savedInstanceState);
		ViewCreateHelper.createView(this, this, savedInstanceState);
	}
	@Override
	public String groupName() {
		return getClass().getName() + hashCode();
	}

	@Override
	protected void onDestroy() {
		String groupName = groupName();
		TaskScheduler.INSTANCE.cancelGroup(groupName);
		HttpHelper.cancelGroup(groupName);
		UnionModuleKt.getUnionLog().d("","cancel group-%s at onDestroy",groupName);
		KeyBoardUtils.fixInputMethodManagerLeak(this);
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
		TaskScheduler.INSTANCE.onPause(groupName());
	}

	@Override
	protected void onResume() {
		super.onResume();
		TaskScheduler.INSTANCE.onResume(groupName());
	}

}
