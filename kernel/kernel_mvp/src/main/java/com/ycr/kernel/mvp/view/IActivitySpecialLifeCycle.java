package com.ycr.kernel.mvp.view;

import android.content.Intent;

/**
 * Created by yuchengren on 2018/7/26.
 */
public interface IActivitySpecialLifeCycle {
	void onRestart();
	void onNewIntent(Intent intent);
}
