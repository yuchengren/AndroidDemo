package com.yuchengren.mvp.app.ui.activity.Test;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yuchengren.mvp.R;
import com.yuchengren.mvp.app.presenter.abs.Presenter;
import com.yuchengren.mvp.app.ui.activity.Base.BaseActivity;
import com.yuchengren.mvp.app.ui.activity.Base.SuperActivity;

/**
 * Created by yuchengren on 2017/12/28.
 */

public class TestActivity extends BaseActivity<Presenter> {

	private Button btn_test;

	@Override
	protected int getLayoutResID() {
		return R.layout.activity_test;
	}

	@Override
	protected void initViews() {
//		btn_test = (Button) findViewById(R.id.btn_test);

	}

	@Override
	protected void initListeners() {
//		btn_test.setOnClickListener(this);
	}

	@Override
	protected void initData() {

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()){
			case R.id.btn_test:
//				testDeadLock();
				testWaitNotify();
				break;
			default:
				break;
		}

	}

	/**
	 * 死锁范例
	 */
	private void testDeadLock() {
		DeadRunnable deadRunnable1 = new DeadRunnable();
		deadRunnable1.flag = 1;
		DeadRunnable deadRunnable2 = new DeadRunnable();
		deadRunnable2.flag = 2;
		new Thread(deadRunnable1).start();
		new Thread(deadRunnable2).start();
	}

	/**
	 * 生产者消费者模式 wait notify
	 */
	private void testWaitNotify(){
		Source source = new Source();
		new Thread(new Producer(source)).start();
		new Thread(new Consumer(source)).start();
	}

}
