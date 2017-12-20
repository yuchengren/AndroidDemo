package com.yuchengren.mvp.app.ui.activity.Base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yuchengren.mvp.R;
import com.yuchengren.mvp.app.presenter.abs.Presenter;

/**
 * Created by yuchengren on 2017/12/18.
 */

public abstract class BaseActivity<P extends Presenter>  extends SuperActivity<P>  implements View.OnClickListener {


	@Override
	protected void init() {
		super.init();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {

	}


	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
	}

	@Override
	public void startActivity(Intent intent, @Nullable Bundle options) {
		super.startActivity(intent, options);
		overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
	}
}
