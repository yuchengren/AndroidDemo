package com.ycr.kernel.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ycr.kernel.mvp.connector.ActivityMvpConnector;
import com.ycr.kernel.mvp.view.IMvpView;

/**
 * Created by yuchengren on 2018/7/26.
 */
public class MvpActivity extends AppCompatActivity implements IMvpView {

	private ActivityMvpConnector mMvpConnector;

	@Override
	public ActivityMvpConnector getMvpConnector() {
		if(mMvpConnector == null){
			synchronized (this){
				if(mMvpConnector == null){
					mMvpConnector = new ActivityMvpConnector();
				}
			}
		}
		return mMvpConnector;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		beforeCreate(savedInstanceState);
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		if(bundle == null){
			bundle = new Bundle();
		}
		getMvpConnector().onCreated(savedInstanceState,getIntent(),bundle);
		afterCreate(savedInstanceState);
	}

	protected void beforeCreate(@Nullable Bundle savedInstanceState) {

	}

	protected void afterCreate(Bundle savedInstanceState) {

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		getMvpConnector().onRestart();
	}

	@Override
	protected void onStart() {
		super.onStart();
		getMvpConnector().onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		getMvpConnector().onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		getMvpConnector().onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		getMvpConnector().onStop();
	}

	@Override
	protected void onDestroy() {
		getMvpConnector().onDestroy();
		getMvpConnector().destroyPresenter();
		super.onDestroy();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		getMvpConnector().onNewIntent(intent);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getMvpConnector().onSaveInstanceState(outState);
	}

}
