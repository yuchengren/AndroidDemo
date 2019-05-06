package com.ycr.kernel.union.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ycr.kernel.mvp.MvpFragment;
import com.ycr.kernel.task.IGroup;
import com.ycr.kernel.task.TaskScheduler;
import com.ycr.kernel.union.UnionModuleKt;
import com.ycr.kernel.union.constants.ReferenceMode;
import com.ycr.kernel.union.mvp.view.IContentView;
import com.ycr.kernel.union.mvp.view.IDefineView;
import com.ycr.kernel.union.mvp.view.ViewCreateHelper;

import org.jetbrains.annotations.Nullable;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Created by yuchengren on 2018/12/10.
 */
public abstract class UnionFragment extends MvpFragment implements IGroup,IDefineView,IContentView {

	private Reference<View> rootViewReference;
	private View rootView;

	@Nullable
	@Override
	public String groupName() {
		return getClass().getName() + hashCode();
	}

	@Override
	protected View inflaterView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = getRootView();
		if(rootView == null || !isInitOnce()){
			rootView = ViewCreateHelper.createView(getContext(), this, savedInstanceState);
		}
		return rootView;
	}

	private boolean isInitOnce() {
		return true;
	}

	private View getRootView() {
		if(rootView != null){
			return rootView;
		}
		if(rootViewReference != null){
			return rootViewReference.get();
		}
		UnionModuleKt.getUnionLog().e(getClass().getName(),"getRootView is null!");
		return null;
	}

	@Override
	public void setContentView(View view) {
		switch (getViewReferenceMode()){
			case ReferenceMode.STRONG :
				rootView = view;
				break;
			case ReferenceMode.SOFT :
				rootViewReference = new SoftReference(view);
				break;
			case ReferenceMode.WEAK :
				rootViewReference = new WeakReference(view);
				break;
			default:
				rootViewReference = new SoftReference(view);
				break;
		}
	}

	private @ReferenceMode int getViewReferenceMode(){
		return ReferenceMode.SOFT;
	}

	@Override
	public void onResume() {
		super.onResume();
		TaskScheduler.INSTANCE.onResume(groupName());
	}

	@Override
	public void onPause() {
		super.onPause();
		TaskScheduler.INSTANCE.onPause(groupName());
	}

	@Override
	public void onDestroy() {
		String groupName = groupName();
		TaskScheduler.INSTANCE.cancelGroup(groupName);
		super.onDestroy();
	}
}
