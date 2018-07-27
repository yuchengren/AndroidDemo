package com.ycr.kernel.mvp.view;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by yuchengren on 2018/7/26.
 */
public interface IBaseLifeCycle {
	/**
	 *
	 * @param savedInstanceState
	 * @param intent
	 * @param bundle 若是Activity则为intent.getExtras(),若是Fragment则为getArguments()
	 */
	void onCreated(Bundle savedInstanceState,Intent intent,Bundle bundle);
	void onStart();
	void onResume();
	void onPause();
	void onStop();
	void onDestroy();
	void onSaveInstanceState(Bundle outState);
	void onActivityResult(int requestCode, int resultCode, Intent data);

}
