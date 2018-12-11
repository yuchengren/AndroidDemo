package com.yuchengren.demo.app.test;

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
