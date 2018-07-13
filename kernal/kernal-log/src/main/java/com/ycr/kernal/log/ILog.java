package com.ycr.kernal.log;

/**
 * Created by yuchengren on 2018/7/11.
 */
public interface ILog {
	void v(String tag,String msg,Object...args);
	void v(String tag,String msg,Throwable tr,Object...args);

	void d(String tag,String msg,Object...args);
	void d(String tag,String msg,Throwable tr,Object...args);

	void i(String tag,String msg,Object...args);
	void i(String tag,String msg,Throwable tr,Object...args);

	void w(String tag,String msg,Object...args);
	void w(String tag,String msg,Throwable tr,Object...args);

	void e(String tag,String msg,Object...args);
	void e(String tag,String msg,Throwable tr,Object...args);
}
