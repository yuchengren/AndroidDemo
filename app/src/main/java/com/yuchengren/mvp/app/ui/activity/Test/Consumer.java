package com.yuchengren.mvp.app.ui.activity.Test;

/**
 * Created by yuchengren on 2018/1/2.
 */

public class Consumer implements Runnable {

	public Source source;

	public Consumer(Source source) {
		this.source = source;
	}

	@Override
	public void run() {
		while(true){
			source.consume();
		}
	}
}
