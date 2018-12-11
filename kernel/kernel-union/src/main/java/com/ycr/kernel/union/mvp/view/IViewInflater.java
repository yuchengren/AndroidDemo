package com.ycr.kernel.union.mvp.view;

import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * Created by yuchengren on 2018/12/10.
 */
public interface IViewInflater {
	View inflateView(@LayoutRes int layoutResId);
}
