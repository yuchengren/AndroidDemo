package com.ycr.kernel.mvp.view;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by yuchengren on 2018/7/26.
 */
public interface IBaseLifeCycle {
	Intent getIntent();
	void setIntent(Intent intent);
	void onCreated(Bundle savedInstanceState);
	void onStart();
	void onResume();
	void onPause();
	void onStop();
	void onDestroy();
	void onSaveInstanceState(Bundle outState);
	void onActivityResult(int requestCode, int resultCode, Intent data);

}
