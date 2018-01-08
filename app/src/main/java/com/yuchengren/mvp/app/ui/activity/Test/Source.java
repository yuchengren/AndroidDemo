package com.yuchengren.mvp.app.ui.activity.Test;

/**
 * Created by yuchengren on 2018/1/2.
 */

public class Source {
	public int productNum;

	public synchronized void produce(){
		if(productNum > 0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		productNum ++;
		System.out.println("当前线程="+Thread.currentThread().getName()+",生产后productNum="+productNum);
		notifyAll();
	}

	public synchronized void consume(){
		if(productNum == 0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		productNum --;
		System.out.println("当前线程="+Thread.currentThread().getName()+",消费后productNum="+productNum);
		notifyAll();
	}
}
