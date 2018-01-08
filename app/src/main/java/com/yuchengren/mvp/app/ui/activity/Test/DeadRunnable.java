package com.yuchengren.mvp.app.ui.activity.Test;

/**
 * Created by yuchengren on 2017/12/28.
 */

public class DeadRunnable implements Runnable {
	private static Object object1 = new Object();
	private static Object object2 = new Object();
	private static int count = 0;

	public int flag;
	@Override
	public void run() {
		if(flag == 1){
			while (true){
				synchronized (object1){
					System.out.println(flag + " object1 " + count);
					synchronized (object2){
						System.out.println(flag + " object2 " + count);
						count ++;
					}
				}
			}
		}else{
			while (true){
				synchronized (object2){
					System.out.println(flag + " object2 "  + count);
					synchronized (object1){
						System.out.println(flag + " object1 " + count);
						count ++;
					}
				}
			}
		}
	}
}
