package com.ycr.kernel.union.mvp.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ycr.kernel.log.engine.ILogEngine;
import com.ycr.kernel.union.UnionModuleKt;

/**
 * Created by yuchengren on 2018/12/10.
 */
public class ViewCreateHelper {
	private static ILogEngine unionLog = UnionModuleKt.getUnionLog();

	public static View createView(Context context, IDefineView defineView,Bundle savedInstanceState){
		View rootView = null;
		int rootLayoutResId = defineView.getRootLayoutResId();
		if(rootLayoutResId > 0){
			defineView.beforeBindView();
			if(defineView instanceof ViewGroup){
				ViewGroup viewGroup = (ViewGroup) defineView;
				ViewGroup.inflate(context,rootLayoutResId,viewGroup);
				rootView = viewGroup;
			}else{
				rootView = inflateView(context, rootLayoutResId);
				if(defineView instanceof IContentView){
					try {
						((IContentView) defineView).setContentView(rootView);
					}catch (Exception e){
						printException(defineView,"setContentView",e);
					}
				}else if(defineView instanceof Activity){
					try {
						((Activity) defineView).setContentView(rootView);
					}catch (Exception e){
						printException(defineView,"setContentView",e);
					}
				}else if(defineView instanceof IViewInflater){
					((IViewInflater) defineView).inflateView(rootLayoutResId);
				}
			}
		}
		if(rootView == null){
			unionLog.i("%s layout id is not defined! ",defineView.getClass().getName());
			return null;
		}
		try {
			defineView.bindView(rootView);
		}catch (Exception e){
			printException(defineView,"bindView",e);
		}
		try {
			defineView.afterBindView(rootView,savedInstanceState);
		}catch (Exception e){
			printException(defineView,"afterBindView",e);
		}
		return rootView;

	}

	public static void printException(Object o,String period,Exception e){
		unionLog.e(e," %1$s exception occur at %2$s ",o.getClass().getName(),period);
	}

	public static View inflateView(Context context,@LayoutRes int layoutResId){
		LayoutInflater inflater = LayoutInflater.from(context);
		return inflater.inflate(layoutResId,null,false);
	}

}
