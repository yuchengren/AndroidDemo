package com.yuchengren.mvp.app.test;

/**
 * Created by yuchengren on 2018/1/2.
 */

public class Producer implements Runnable {

	public Source source;

	public Producer(Source source) {
		this.source = source;
	}

	@Override
	public void run() {
		while(true){
			source.produce();
		}
	}
}
