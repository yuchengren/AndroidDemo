package com.ycr.kernel.union.mvp.view;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * Created by yuchengren on 2018/12/10.
 */
public interface IDefineView {
	@LayoutRes int getRootLayoutResId();
	void beforeBindView();
	void bindView(View rootView);
	void afterBindView(View rootView,Bundle savedInstanceState);
}
